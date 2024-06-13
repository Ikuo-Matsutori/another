import { useState, useEffect } from "react";
import Input from "./Input.jsx";
import Table from "./Table.jsx";
import "./App.css";

function App() {
  const [list, setList] = useState([]);

  const listCreator = async () => {
    const fetchedList = await fetch("/api/list");
    const listJson = await fetchedList.json();
    const result = await Promise.all(
      listJson.map((ele) => fetch(`/api/price/${ele.ticker_symbol}`))
    );
    const resultJson = await Promise.all(result.map((ele) => ele.json()));
    const bitObj = listJson.map((ele, index) => ({
      ...ele,
      current_price: Math.round(resultJson[index]),
    }));
    setList(bitObj);
  };

  useEffect(() => {
    listCreator();
  }, []);

  console.log(list);

  const postButton = () => {
    fetch("/api/list", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        ticker_symbol: document.querySelector("#ticker-symbol").value,
        wallet: document.querySelector("#wallet").value,
        quantity: document.querySelector("#quantity").value,
        unit_price: document.querySelector("#unit-price").value,
        total_price: document.querySelector("#total-price").value,
      }),
    });
    listCreator();
  };

  const deleteButton = (id) => {
    fetch(`/api/list/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() =>
      fetch("/api/list")
        .then((res) => res.json())
        .then((data) => setList(data))
    );
    listCreator();
  };

  const totalProfit = list.reduce(
    (acc, cur) => acc + cur.current_price * cur.quantity - cur.total_price,
    0
  );

  const comment = (profit) => {
    if (profit < 0) {
      return "損してて草";
    } else if (0 < profit && profit <= 10000) {
      return "やる意味無くて草";
    } else if (10000 < profit && profit <= 100000) {
      return "これぐらいで浮かれてて草";
    } else if (100000 < profit && profit <= 1000000) {
      return "ちょっと儲かってて草";
    } else if (1000000 < profit && profit <= 10000000) {
      return "副業レベルで草";
    } else if (10000000 < profit && profit <= 100000000) {
      return "年収より儲かってて草";
    } else if (100000000 < profit) {
      return "まだ働いてて草";
    }
  };

  return (
    <div>
      <h1>ドッキドキ❤️暗号資産管理</h1>
      <h1>〜投資に夢見てて草〜</h1>
      <div className="result">
        <p id="total-profit">
          損益計：
          {Math.round(totalProfit) || "Loading..."}円
        </p>
        <p id="profit-ratio">コメント：{comment(totalProfit)}</p>
      </div>
      <Input className="input-form" postButton={postButton}></Input>
      <Table list={list} deleteButton={deleteButton}></Table>
    </div>
  );
}

export default App;

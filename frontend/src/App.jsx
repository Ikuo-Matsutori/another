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

  const postButton = async() => {

     const newItem = {
         　ticker_symbol: document.querySelector("#ticker-symbol").value,
           wallet: document.querySelector("#wallet").value,
           quantity: document.querySelector("#quantity").value,
           unit_price: document.querySelector("#unit-price").value,
           total_price: document.querySelector("#total-price").value,
     };

    await fetch("/api/list", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newItem),
    });
    const response = await fetch(`/api/price/${newItem.ticker_symbol}`);
    const price = await response.json();
    newItem.current_price = Math.round(price);
    setList((oldList) => [...oldList, newItem]);
  };

  const deleteButton = async (id) => {
    await fetch(`/api/list/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    })
    setList((oldList) => oldList.filter((item) => item.id !== id));
  };

  const totalProfit = list.reduce(
    (acc, cur) => acc + cur.current_price * cur.quantity - cur.total_price,
    0
  );

  const comment = (profit) => {
    if (profit < 0) {
      return "損してて草";
    } else if (0 < profit && profit <= 10000) {
      return "すばらしい、少し利益が出ています";
    } else if (10000 < profit && profit <= 100000) {
      return "すばらしい、この調子でまずは百万円を目指しましょう";
    } else if (100000 < profit && profit <= 1000000) {
      return "すごい、ボーナスぐらいの利益がでています";
    } else if (1000000 < profit && profit <= 10000000) {
      return "すばらしい！目指せ1000万円！";
    } else if (10000000 < profit && profit <= 100000000) {
      return "ここまで来れたあなたは凄い、きっと億り人になれる！";
    } else if (100000000 < profit) {
      return "おめでとう！目標達成です";
    }
  };

  return (
    <div className="main-class">
      <h1>暗号資産管理シート</h1>
      <h1>〜目指せ億り人〜</h1>
      <div className="result">
        <p id="total-profit">
          損益計：
          {Math.round(totalProfit) || "Loading..."}円
        </p>
        <p id="profit-ratio">{comment(totalProfit)}</p>
      </div>
      <Input className="input-form" postButton={postButton}></Input>
      <Table list={list} deleteButton={deleteButton}></Table>
    </div>
  );
}

export default App;

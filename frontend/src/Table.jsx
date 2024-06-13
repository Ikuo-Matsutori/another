import "./Table.css";

export default function Table(props) {
  const { list, deleteButton } = props;

  return (
    <table>
      <thead>
        <tr>
          <th>種類</th>
          <th>ウォレット</th>
          <th>数量</th>
          <th>単価(円)</th>
          <th>投入金額(円)</th>
          <th>現在価格(円)</th>
          <th>損益(円)</th>
          <th>利益率(%)</th>
        </tr>
      </thead>
      <tbody>
        {list.map((obj) => (
          <tr key={obj.id}>
            <td>{obj.ticker_symbol}</td>
            <td>{obj.wallet}</td>
            <td>
              {obj.quantity >= 1 ? Math.round(obj.quantity) : obj.quantity}
            </td>
            <td>
              {obj.unit_price >= 1
                ? Math.round(obj.unit_price)
                : obj.unit_price}
            </td>
            <td>{Math.round(obj.total_price)}</td>
            <td>{obj.current_price || "Loading..."}</td>
            <td>
              {obj.current_price * obj.quantity - obj.total_price ||
                "Loading..."}
            </td>
            <td>
              {Math.round(
                ((obj.current_price * obj.quantity - obj.total_price) /
                  obj.total_price) *
                  100
              ) || "Loading..."}
            </td>
            <button onClick={() => deleteButton(obj.id)}>
              消して大丈夫そ？
            </button>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

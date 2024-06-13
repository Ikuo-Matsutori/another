export default function Input(props) {
  const { className, postButton } = props;
  return (
    <div className={className}>
      <div className="input-area">
        <div className="input">
          <label htmlFor="ticker-symbol">種別(ticker)</label>
          <input id="ticker-symbol"></input>
        </div>
        <div className="input">
          <label htmlFor="wallet">ウォレット</label>
          <input id="wallet"></input>
        </div>
        <div className="input">
          <label htmlFor="quantity">数量</label>
          <input id="quantity"></input>
        </div>
        <div className="input">
          <label htmlFor="unit-price">単価(円)</label>
          <input id="unit-price"></input>
        </div>
        <div className="input">
          <label htmlFor="total-price">投入金額(円)</label>
          <input id="total-price"></input>
        </div>
      </div>
      <button className="input-button" onClick={postButton}>
        追加しておk
      </button>
    </div>
  );
}

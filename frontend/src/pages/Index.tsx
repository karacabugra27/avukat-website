import { btnPrimary, btnSecondary, linkPrimary } from "../styles";
import { Link } from "react-router-dom";

export default function Index() {
  return (
    <div className="card bg-base-100 w-96 shadow-sm">
      <figure>
        <img
          src="https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp"
          alt="Shoes" />
      </figure>
      <div className="card-body">
        <h2 className="card-title">Card Title</h2>
        <p>A card component has a figure, a body part, and inside body there are title and actions parts</p>
        <div className="card-actions justify-end">
          <button className={btnPrimary}>Buy Now</button>
          <button className={btnSecondary}>Buy Later</button>
          <Link to={'/iletisim'} className={linkPrimary}>İletişime Geç</Link>
        </div>
      </div>
    </div>
  );
}
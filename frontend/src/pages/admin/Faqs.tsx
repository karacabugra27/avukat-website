import { Plus } from "lucide-react";
import { Link } from "react-router-dom";

export default function Faqs() {
  return(
    <>
      <div className="flex justify-between">
        <div>
          <h1 className="text-3xl font-bold">Sık Sorulanlar</h1>
          <p className="text-slate-600">Sık sorulan soruları yönetin.</p>
        </div>
        <div>
          <Link to={'add'} className="flex gap-2 px-3 py-2 rounded bg-green-600 hover:bg-green-700 text-white"><Plus width={20} />Soru Ekle</Link>
        </div>
      </div>
    </>
  );
}
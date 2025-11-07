import { useState } from "react";
import { ArrowLeft } from "lucide-react";
import { Link } from "react-router-dom";
import toast from "react-hot-toast";
import axios from "axios";

export default function AddFaq() {
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post("http://localhost:8080/api/faq", { question: question, answer: answer});
      if(response.status === 201) {
        toast.success("Soru eklendi.");
      }
    }
    catch(error: any) {
      console.log(error);
      toast.error("İşlem başarısız.")
    }
    finally {
      setLoading(false);
    }
  }
  
  return (
    <>
      <div className="flex justify-between">
        <div>
          <h1 className="text-3xl font-bold">Soru Ekleyin</h1>
          <p className="text-slate-600">Yeni soru ekleyin.</p>
        </div>
        <div>
          <Link to={'/admin/faqs'} className="flex gap-2 px-3 py-2 rounded bg-gray-900 hover:bg-gray-700 text-white"><ArrowLeft width={20} />Geri</Link>
        </div>
      </div>
      <div className="mt-12">
        <form
          onSubmit={handleSubmit}>
          <div>
            <fieldset className="fieldset">
              <legend className="fieldset-legend">Soru</legend>
              <input
                type="text"
                onChange={(e) => setQuestion(e.target.value)}
                className="input input-md focus:outline-none w-full"
                placeholder="Soru" 
                required />
            </fieldset>
          </div>
          <div>
            <fieldset className="fieldset">
              <legend className="fieldset-legend">Cevap</legend>
              <textarea 
                onChange={(e) => setAnswer(e.target.value)}
                rows={5}
                className="textarea w-full focus:outline-none"
                placeholder="Cevap..."
                required ></textarea>
            </fieldset>
          </div>
          <div className="mt-6">
            <button type="submit" disabled={loading} className="px-12 py-2 rounded bg-green-600 hover:bg-green-700 text-white hover:cursor-pointer disabled:cursor-not-allowed disabled:bg-green-500" >
              {loading ? "Ekleniyor..." : "Ekle"}
            </button>
          </div>
        </form>
      </div>
    </>
  );
}
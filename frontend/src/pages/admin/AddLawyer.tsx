import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import toast from "react-hot-toast";
import { Link } from "react-router-dom";
import { ArrowLeft } from "lucide-react";

export default function AddLawyer() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const response = await axios.post("http://localhost:8080/api/admin/lawyers", { email: email }, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        }
      });
      if (response.status === 201) {
        toast.success("Hesap başarılı bir şekilde oluşturuldu.");
        navigate("/admin/lawyers");
      }
    }
    catch (error: any) {
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
          <h1 className="text-3xl font-bold">Avukat Hesabı Ekleyin</h1>
          <p className="text-slate-600">Yeni avukat hesabı ekleyin.</p>
        </div>
        <div>
          <Link to={'/admin/lawyers'} className="flex gap-2 px-3 py-2 rounded bg-gray-900 hover:bg-gray-700 text-white"><ArrowLeft width={20} />Geri</Link>
        </div>
      </div>
      <div className="mt-12">
        <form
          onSubmit={handleSubmit}>
          <div>
            <fieldset className="fieldset">
              <legend className="fieldset-legend">E-posta</legend>
              <input type="text" onChange={(e) => setEmail(e.target.value)} className="input input-md focus:outline-none w-full" placeholder="E-posta" />
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
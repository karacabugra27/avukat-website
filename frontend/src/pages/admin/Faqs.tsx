import { Plus, Trash2, Eye } from "lucide-react";
import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import toast from "react-hot-toast";
import type { Faq } from "../../types";

export default function Faqs() {
  const [faqs, setFaqs] = useState<Faq[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);
  const [answer, setAnswer] = useState("");
  const [question, setQuestion] = useState("");
  let count = 1;

  async function getFaqs() {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/faq");
      if (response.status === 200) {
        setFaqs(response.data);
      }
    }
    catch (error: any) {
      console.log(error);
      setError(true);
    }
    finally {
      setLoading(false);
    }
  }

  async function deleteFaq(id: number) {
    const confirm = window.confirm("Bu soruyu silmek istediğinize emin misiniz?");
    if (!confirm) return;

    setLoading(true);
    try {
      const response = await axios.delete(`http://localhost:8080/api/faq/${id}`);
      if (response.status === 204) {
        toast.success("Soru silindi.");
        await getFaqs();
      }
    }
    catch (error: any) {
      console.log(error);
      toast.error("Silme işlemi başarısız.");
    }
    finally {
      setLoading(false);
    }
  }

  function showAnswer(question:string, answer: string) {
    setQuestion(question);
    setAnswer(answer);
    const modal = document.getElementById("my_modal_5");
    modal?.showModal();
  }

  useEffect(() => {
    getFaqs();
  }, []);

  return (
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
      <div className="mt-12">
        {loading ? (
          <h1 className="text-xl">Yükleniyor...</h1>
        ) : (
          error ? (
            <h1 className="text-xl">Bir hata oluştu</h1>
          ) : (
            faqs.length === 0 ? (
              <h1 className="text-xl">Henüz soru eklenmemiş.</h1>
            ) : (
              <div className="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                <table className="table">
                  {/* head */}
                  <thead>
                    <tr>
                      <th></th>
                      <th>Soru</th>
                      <th>İşlemler</th>
                    </tr>
                  </thead>
                  <tbody>
                    {faqs.map(faq => {
                      return (
                        <tr className="hover:bg-gray-50" key={faq.id}>
                          <td>{count++}</td>
                          <td className="font-bold w-2xl">{faq.question}</td>
                          <th>
                            <div className="flex gap-2">
                              <button className="btn btn-sm" onClick={() => showAnswer(faq.question, faq.answer)}>
                                <Eye width={15} />
                              </button>
                              <button
                                onClick={() => deleteFaq(faq.id)}
                                className="btn btn-sm bg-red-500 hover:bg-red-600 text-white">
                                <Trash2 width={15} />
                              </button>
                            </div>
                          </th>
                        </tr>
                      );
                    })}
                  </tbody>
                </table>
              </div>
            )
          )
        )}
      </div>
      {/* Open the modal using document.getElementById('ID').showModal() method */}
      <dialog id="my_modal_5" className="modal modal-bottom sm:modal-middle">
        <div className="modal-box max-w-4xl">
          <h3 className="font-bold text-lg">{question}</h3>
          <p className="py-4">{answer}</p>
          <div className="modal-action">
            <form method="dialog">
              {/* if there is a button in form, it will close the modal */}
              <button className="btn">Kapat</button>
            </form>
          </div>
        </div>
      </dialog>
    </>
  );
}
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import toast from "react-hot-toast";
import type { Lawyer } from "../../types";
import { Plus, UserRound, Trash2 } from "lucide-react";

export default function Lawyers() {
  const [lawyers, setLawyers] = useState<Lawyer[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  async function getLawyers() {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/lawyers/experiences");
      if (response.status === 200) {
        setLawyers(response.data);
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

  async function deleteLawyer(id: number) {
    const confirm = window.confirm("Bu avukatı silmek istediğinize emin misiniz?");
    if(!confirm) return;

    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const response = await axios.delete(`http://localhost:8080/api/admin/lawyers/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        }
      });
      if(response.status === 204) {
        toast.success("Avukat silindi.");
        await getLawyers();
      }
    }
    catch(error: any) {
      console.log(error);
      toast.error("Silme işlemi başarısız.");
    }
    finally {
      setLoading(false);
    }
  } 

  useEffect(() => {
    getLawyers();
  }, [])

  if (error) {
    return (
      <h1>Bir hata oluştu.</h1>
    );
  }

  return (
    <>
      <div className="flex justify-between">
        <div>
          <h1 className="text-3xl font-bold">Avukatlar</h1>
          <p className="text-slate-600">Avukatları yönetin.</p>
        </div>
        <div>
          <Link to={'add'} className="flex gap-2 px-3 py-2 rounded bg-green-600 hover:bg-green-700 text-white"><Plus width={20} />Avukat Ekle</Link>
        </div>
      </div>
      <div className="mt-12">
        {loading ? (
          <h1 className="text-xl">Yükleniyor...</h1>
        ) : (
          lawyers.length === 0 ? (
            <h1>Henüz avukat eklenmemiş.</h1>
          ) : (
            <div className="overflow-x-auto">
              <table className="table">
                {/* head */}
                <thead>
                  <tr>
                    <th>Avukat</th>
                    <th>Uzmanlık Alanları</th>
                    <th>İşlemler</th>
                  </tr>
                </thead>
                <tbody>
                  {lawyers.map(lawyer => {
                    return (
                      <tr className="hover:bg-gray-50" key={lawyer.id}>
                        <td>
                          <div className="flex items-center gap-3">
                            <div className="avatar">
                              <div className="mask mask-squircle h-12 w-12">
                                {lawyer.profileImage ? (
                                  <img
                                    src={lawyer.profileImage}
                                    alt="Avatar Tailwind CSS Component" />
                                ) : (
                                  <div className="bg-gray-200 flex justify-center items-center h-full">
                                    <UserRound width={18} />
                                  </div>
                                )}
                              </div>
                            </div>
                            <div>
                              <div className="font-bold">{lawyer.firstName ?? "-"} {lawyer.lastName ?? "-"}</div>
                              <div className="text-sm opacity-50">{lawyer.email}</div>
                            </div>
                          </div>
                        </td>
                        <td>
                          <div className="flex flex-wrap gap-3">
                            {lawyer.experiences.length !== 0 ? (
                              lawyer.experiences.map(experience => {
                                return(
                                  <div
                                    className="px-2 py-1 rounded bg-gray-100 border-1 border-gray-500 text-gray-500 hover:bg-gray-200"
                                    key={experience.id}>
                                    {experience.title}
                                  </div>
                                );
                              })
                            ) : (
                              <span>Uzmanlık alanı eklenmemiş</span>
                            )}
                          </div>
                        </td>
                        <th>
                          <div className="flex gap-2">
                            <button className="btn">Detaylar</button>
                            <button
                              onClick={() => deleteLawyer(lawyer.id)}
                              className="btn bg-red-500 hover:bg-red-600 text-white">
                                <Trash2 width={15}/>
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
        )}
      </div >
    </>
  );
}
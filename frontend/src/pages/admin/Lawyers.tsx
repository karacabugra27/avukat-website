import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import type { Lawyer } from "../../types";
import { Plus } from "lucide-react";

export default function Lawyers() {
  const [lawyers, setLawyers] = useState<Lawyer[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  async function getLawyers() {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/lawyers");
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
            <table>
              <thead>
                <tr>
                  <th>Ad</th>
                  <th>Soyad</th>
                  <th>E-Posta</th>
                </tr>
              </thead>
              <tbody>
                {lawyers.map(lawyer => {
                  return (
                    <tr>
                      <td>{lawyer.firstName ?? "-"}</td>
                      <td>{lawyer.lastName ?? "-"}</td>
                      <td>{lawyer.email}</td>
                    </tr>
                  )
                })}
              </tbody>
            </table>
          )
        )}
      </div>
    </>
  );
}
import axios from "axios";
import toast from "react-hot-toast";
import { Toaster } from "react-hot-toast";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post("http://localhost:8080/auth/login", { email, password });
      if(response.status === 200) {
        localStorage.removeItem("token");
        localStorage.removeItem("expiresAt");
        localStorage.removeItem("role");
        //localStorage.removeItem("id");

        localStorage.setItem("token", response.data.accessToken);
        localStorage.setItem("expiresAt", response.data.expiresAt);
        localStorage.setItem("role", response.data.role);
        //localStorage.setItem("id", response.data.id);

        toast.success("Başarıyla giriş yaptınız.");
        if(localStorage.getItem("role") === "SUPER_ADMIN") {
          navigate("/admin");
        }
        else {
          navigate("/lawyer");
        }
      }
    }
    catch(error: any) {
      console.log(error);
      toast.error("Giriş işlemi başarısız");
    }
    finally {
      setLoading(false);
    }
  }

  return (
    <>
      <div className="flex items-center justify-center h-screen bg-gray-200">
        <div className="bg-white shadow-2xl rounded-2xl w-full max-w-md p-8">
          <div className="text-center mb-6">
            <h1 className="text-3xl font-bold mt-3 text-gray-800">Hukuk Bürosu</h1>
            <p className="text-gray-500">Lütfen giriş yapınız</p>
          </div>
          <form className="space-y-4" onSubmit={handleSubmit}>
            <div>
              <label className="block text-gray-700 font-medium mb-1">E-Posta</label>
              <div className="flex items-center border rounded-lg px-3 py-2 focus-within:ring-1">
                <input type="text" onChange={(e) => setEmail(e.target.value) } placeholder="E-Postanızı giriniz" required
                  className="w-full outline-none text-gray-700" />
              </div>
            </div>
            <div>
              <label className="block text-gray-700 font-medium mb-1">Şifre</label>
              <div className="flex items-center border rounded-lg px-3 py-2 focus-within:ring-1">
                <input type="password" onChange={(e) => setPassword(e.target.value)} placeholder="Şifrenizi giriniz" required
                  className="w-full outline-none text-gray-700" />
              </div>
            </div>

            <button type="submit"
              disabled={loading}
              className="w-full disabled:cursor-not-allowed disabled:bg-gray-700 bg-gray-900 text-white py-2 rounded-lg font-medium hover:bg-gray-700 hover:cursor-pointer transition mt-3">
              {loading ? "Yükleniyor..." : "Giriş yap"}
            </button>
          </form>
        </div>
      </div>
      <Toaster position="top-center" toastOptions={{duration: 3000}}/>
    </>
  );
}
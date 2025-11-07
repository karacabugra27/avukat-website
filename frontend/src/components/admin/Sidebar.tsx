import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import { LayoutDashboard, UserRound, MessageCircleQuestionMark, Mail, CircleUserRound, Settings, LogOut, CalendarDays } from "lucide-react";

export default function Sidebar() {
  const navigate = useNavigate();

  function handleLogout() {
    const confirm = window.confirm("Çıkış yapmak istiyor musunuz?");
    if(!confirm) return;

    localStorage.removeItem("token");
    localStorage.removeItem("expiresAt");
    localStorage.removeItem("role");
    toast.success("Başarıyla çıkış yaptınız.");
    navigate("/login", { replace: true });
  }

  return (
    <aside className="w-72 min-h-screen bg-gray-900 text-gray-200 flex flex-col p-4 shadow-lg">
      <div className="mb-8">
        <h1 className="text-2xl font-bold tracking-wide text-white">Hukuk Bürosu</h1>
      </div>
      <nav className="flex flex-col space-y-2">
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <LayoutDashboard width={20} strokeWidth={1.5} />
          <span className="ml-3">Dashboard</span>
        </Link>
        <Link to="/admin/lawyers"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <UserRound width={20} strokeWidth={1.5} />
          <span className="ml-3">Avukatlar</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <CalendarDays width={20} strokeWidth={1.5} />
          <span className="ml-3">Randevular</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <MessageCircleQuestionMark width={20} strokeWidth={1.5} />
          <span className="ml-3">SSS</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <Mail width={20} strokeWidth={1.5} />
          <span className="ml-3">Mesajlar</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <CircleUserRound width={20} strokeWidth={1.5} />
          <span className="ml-3">Kullanıcılar</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <Settings width={20} strokeWidth={1.5} />
          <span className="ml-3">Ayarlar</span>
        </Link>
        <div className="border-t border-gray-600 pt-2">
          <button onClick={handleLogout}
            className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition w-full hover:cursor-pointer">
            <LogOut width={20} strokeWidth={1.5} />
            <span className="ml-3">Çıkış</span>
          </button>
        </div>
      </nav>
    </aside>
  );
}
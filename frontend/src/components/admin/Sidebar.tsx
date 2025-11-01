import { Link } from "react-router-dom";
import { LayoutDashboard, UserRound, MessageCircleQuestionMark, Mail, CircleUserRound, Settings, LogOut } from "lucide-react";

export default function Sidebar() {
  return (
    <aside className="w-72 min-h-screen bg-gray-900 text-gray-200 flex flex-col p-4 shadow-lg">
      <div className="mb-8">
        <h1 className="text-2xl font-bold tracking-wide text-white">Hukuk Bürosu</h1>
      </div>
      <nav className="flex flex-col space-y-2">
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <LayoutDashboard />
          <span className="ml-3">Dashboard</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <UserRound />
          <span className="ml-3">Avukatlar</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <MessageCircleQuestionMark />
          <span className="ml-3">SSS</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <Mail />
          <span className="ml-3">Mesajlar</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <CircleUserRound />
          <span className="ml-3">Kullanıcılar</span>
        </Link>
        <Link to="/admin"
          className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
          <Settings />
          <span className="ml-3">Ayarlar</span>
        </Link>
        <div className="border-t border-gray-600 pt-2">
          <Link to="/admin"
            className="flex items-center p-3 rounded-lg hover:bg-gray-800 transition">
            <LogOut />
            <span className="ml-3">Çıkış</span>
          </Link>
        </div>
      </nav>
    </aside>
  );
}
import { useState } from "react";
import { Link } from "react-router-dom";
import { navLink, btnSecondary } from "../styles";
import { Menu, X } from "lucide-react";

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="fixed top-0 left-0 w-full z-50 flex justify-between items-center bg-gray-500/70 backdrop-blur-xs px-12 py-8">
      {/* Logo */}
      <div>
        <h1 className="text-2xl md:text-3xl font-semibold">
          Hukuk Bürosu
        </h1>
      </div>

      {/* Menü - Masaüstü */}
      <ul className="hidden md:flex gap-6 text-lg">
        <li><Link to="/" className={navLink}>Avukatlar</Link></li>
        <li><Link to="/" className={navLink}>Kurumsal Danışmanlık</Link></li>
        <li><Link to="/" className={navLink}>Sık Sorulanlar</Link></li>
        <li><Link to="/" className={navLink}>Blog</Link></li>
      </ul>

      {/* Buton - Masaüstü */}
      <div className="hidden md:block">
        <button className={btnSecondary}>Giriş</button>
      </div>

      {/* Hamburger Menü - Mobil */}
      <button
        onClick={() => setIsOpen(true)}
        className="md:hidden text-white text-3xl focus:outline-none"
      >
        <Menu />
      </button>

      {/* Drawer (Mobil Menü) */}
      <div
        className={`fixed top-0 right-0 h-full w-64 bg-white backdrop-blur-md shadow-lg transform transition-transform duration-300 z-50
          ${isOpen ? "translate-x-0" : "translate-x-full"}`}
      >
        {/* Drawer Header */}
        <div className="flex justify-between items-center p-6 border-b border-gray-700">
          <h2 className="text-xl font-semibold">Menü</h2>
          <button onClick={() => setIsOpen(false)} className="text-3xl">
            <X />
          </button>
        </div>

        {/* Drawer İçeriği */}
        <ul className="flex flex-col gap-6 p-6 text-lg">
          <li>
            <Link to="/" className={navLink} onClick={() => setIsOpen(false)}>
              Avukatlar
            </Link>
          </li>
          <li>
            <Link to="/" className={navLink} onClick={() => setIsOpen(false)}>
              Kurumsal Danışmanlık
            </Link>
          </li>
          <li>
            <Link to="/" className={navLink} onClick={() => setIsOpen(false)}>
              Sık Sorulanlar
            </Link>
          </li>
          <li>
            <Link to="/" className={navLink} onClick={() => setIsOpen(false)}>
              Blog
            </Link>
          </li>
        </ul>

        {/* Drawer Butonu */}
        <div className="p-6">
          <button
            className={`${btnSecondary} w-full`}
            onClick={() => setIsOpen(false)}
          >
            Giriş
          </button>
        </div>
      </div>

      {/* Drawer Arkası (Backdrop) */}
      {isOpen && (
        <div
          onClick={() => setIsOpen(false)}
          className="fixed inset-0 bg-black/40 backdrop-blur-sm z-40"
        ></div>
      )}
    </nav>
  );
}

import { useState } from "react";
import { Link } from "react-router-dom";
import { HashLink } from 'react-router-hash-link';
import { navLink, btnSecondary } from "../../styles";
import { Menu, X } from "lucide-react";

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="fixed top-0 left-0 w-full z-50 flex justify-between items-center bg-black/60 px-12 py-8">
      {/* Logo */}
      <div>
        <h1 className="text-2xl md:text-3xl font-semibold text-white">
          Hukuk Bürosu
        </h1>
      </div>

      {/* Menü - Masaüstü */}
      <ul className="hidden md:flex items-center gap-6 text-lg">
        <li><HashLink to="/#index" className={navLink}>Ana Sayfa</HashLink></li>
        <li><HashLink smooth to="/#avukatlar" className={navLink}>Avukatlar</HashLink></li>
        <li><Link to="/" className={navLink}>Randevu Oluştur</Link></li>
        <li><HashLink smooth to="/#sss" className={navLink}>Sık Sorulanlar</HashLink></li>
        <li><HashLink to="/iletisim/#form" className={navLink}>İletişim</HashLink></li>
        <li><button className={btnSecondary}>Giriş</button></li>
      </ul>

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
            <HashLink to="/#index" className="text-black" onClick={() => setIsOpen(false)}>
              Ana Sayfa
            </HashLink>
          </li>
          <li>
            <HashLink smooth to="/#avukatlar" className="text-black" onClick={() => setIsOpen(false)}>
              Avukatlar
            </HashLink>
          </li>
          <li>
            <Link to="/" className="text-black" onClick={() => setIsOpen(false)}>
              Randevu Oluştur
            </Link>
          </li>
          <li>
            <HashLink to="/#sss" className="text-black" onClick={() => setIsOpen(false)}>
              Sık Sorulanlar
            </HashLink>
          </li>
          <li>
            <HashLink to="/iletisim/#form" className="text-black" onClick={() => setIsOpen(false)}>
              İletişim
            </HashLink>
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

import { footerLink } from "../styles";
import { Instagram, Twitter, Facebook, Linkedin } from "lucide-react";
import { Link } from "react-router-dom";
import { HashLink } from "react-router-hash-link";

export default function Footer() {
  return (
    <>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 p-6 lg:p-12 gap-6 bg-gray-900 text-white">
        <div className="col-span-1">
          <h1 className="text-3xl">Hukuk Bürosu</h1>
          <p className="text-sm mt-2 text-gray-400">Lorem ipsum dolor, sit amet consectetur adipisicing elit. Odit obcaecati tenetur, fugiat voluptatibus consequatur facere suscipit architecto amet.</p>
        </div>
        <div className="col-span-1">
          <h1 className="text-xl inline-block border-b-1 border-primary">Bağlantılar</h1>
          <div className="mt-4">
            <ul className="flex flex-col gap-2">
              <li><HashLink to={'/#index'} className={footerLink} >Ana Sayfa</HashLink></li>
              <li><HashLink smooth to={'/#avukatlar'} className={footerLink} >Avukatlar</HashLink></li>
              <li><Link to={'/'} className={footerLink} >Randevu Oluştur</Link></li>
              <li><HashLink smooth to={'/#sss'} className={footerLink} >Sık Sorulanlar</HashLink></li>
              <li><HashLink to={'/iletisim/#form'} className={footerLink} >İletişim</HashLink></li>
            </ul>
          </div>
        </div>
        <div className="col-span-1">
          <h1 className="text-xl inline-block border-b-1 border-primary">Sosyal Medya</h1>
          <div className="mt-4">
            <ul className="flex flex-col gap-2">
              <li><Link to={'https://facebook.com'} target="_blank" className={footerLink} ><Facebook width={20} strokeWidth={1.25} className="inline-block mr-2" />Facebook</Link></li>
              <li><Link to={'https://instagram.com'} target="_blank" className={footerLink} ><Instagram width={20} strokeWidth={1.25} className="inline-block mr-2" />Instagram</Link></li>
              <li><Link to={'https://x.com'} target="_blank" className={footerLink} ><Twitter width={20} strokeWidth={1.25} className="inline-block mr-2" />Twitter</Link></li>
              <li><Link to={'https://linkedin.com'} target="_blank" className={footerLink} ><Linkedin width={20} strokeWidth={1.25} className="inline-block mr-2" />LinkedIn</Link></li>
            </ul>
          </div>
        </div>
        <div className="col-span-1">
          <h1 className="text-xl inline-block border-b-1 border-primary">Bize Ulaşın</h1>
          <div className="mt-4">
            <ul className="flex flex-col gap-2 text-gray-400">
              <li><span className="font-bold text-gray-300">Telefon: </span>123123123</li>
              <li><span className="font-bold text-gray-300">E-Posta: </span>demo@email.com</li>
              <li><span className="font-bold text-gray-300">Whatsapp: </span>123123123</li>
              <li><span className="font-bold text-gray-300">Adress: </span>Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptatem ipsam quia sapiente.</li>
            </ul>
          </div>
        </div>
      </div>
      <footer className="footer sm:footer-horizontal footer-center bg-gray-900 text-gray-400 p-4 border-t border-t-gray-700">
        <aside>
          <p>Copyright © {new Date().getFullYear()} - All right reserved by Hukuk Bürosu</p>
        </aside>
      </footer>
    </>
  );
}
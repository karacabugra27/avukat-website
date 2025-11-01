import { Send, User, AtSign, Phone, Tag, MessageCircle, Info, MapPin, Share2 } from "lucide-react";
import { Instagram, Facebook, Twitter, Linkedin } from "lucide-react";
import { Link } from "react-router-dom";

import { btnPrimary } from "../../styles";

export default function Contact() {
  const inputStyles = "px-3 py-2 text-sm text-black w-full border-1 border-gray-300 rounded-lg focus:outline-none focus:ring-1 focus:ring-primary";

  function handleFormSubmit(e: React.FormEvent) {
    e.preventDefault();
  }

  return (
    <>
      <div id="form" className="px-6 lg:px-24 pt-32 pb-12 grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="col-span-1 lg:col-span-2 bg-white rounded-xl border-1 border-gray-300">
          <form action="" onSubmit={handleFormSubmit}
            className="grid grid-cols-2 px-8 pb-8 gap-x-6 gap-y-2">
            <div className="flex flex-col items-center col-span-2 py-8 border-b-1 border-b-gray-300">
              <Send className="text-3xl" />
              <div className="text-2xl">Mesaj Gönderin</div>
              <p>Formu doldurup mesaj göndererek bize ulaşabilirsiniz.</p>
            </div>
            <div className="col-span-2">
              <fieldset className="fieldset">
                <legend className="fieldset-legend"><User width={18} strokeWidth={1.5} />Ad Soyad</legend>
                <input type="text" className={inputStyles} placeholder="Ad Soyad" />
              </fieldset>
            </div>
            <div className="col-span-1">
              <fieldset className="fieldset">
                <legend className="fieldset-legend"><AtSign width={18} strokeWidth={1.5} />E-posta</legend>
                <input type="mail" className={inputStyles} placeholder="ornek@email.com" />
              </fieldset>
            </div>
            <div className="col-span-1">
              <fieldset className="fieldset">
                <legend className="fieldset-legend"><Phone width={18} strokeWidth={1.5} />Telefon</legend>
                <input type="text" className={inputStyles} placeholder="0555 555 55 55" />
              </fieldset>
            </div>
            <div className="col-span-2">
              <fieldset className="fieldset">
                <legend className="fieldset-legend"><Tag width={18} strokeWidth={1.5} />Konu</legend>
                <select name="" id="" className={inputStyles}>
                  <option value="">Konu Seçiniz</option>
                  <option value="">Genel Bilgi</option>
                  <option value="">Görüş ve Öneriler</option>
                  <option value="">Şikayet</option>
                  <option value="">Diğer</option>
                </select>
              </fieldset>
            </div>
            <div className="col-span-2">
              <fieldset className="fieldset">
                <legend className="fieldset-legend"><MessageCircle width={18} strokeWidth={1.5} />Mesajınız</legend>
                <textarea name="" id="" placeholder="Mesajınız..." className={inputStyles} rows={5}></textarea>
                <p className="label">En az 10 karakter</p>
              </fieldset>
            </div>
            <div className="col-span-2 flex justify-end">
              <button className={`${btnPrimary} px-16`}><Send width={18} strokeWidth={1.5} />Gönder</button>
            </div>
          </form>
        </div>
        <div className="col-span-1">
          <div className="flex flex-col gap-3 p-6 bg-white rounded-xl border-1 border-gray-300">
            <div className="flex items-center text-xl border-b-1 border-b-gray-300 pb-3">
              <Info className="inline-block mr-2" />
              <span className="font-semibold">İletişim Bilgileri</span>
            </div>
            <div className="flex gap-3 items-center p-4 text-x bg-gray-50 hover:bg-gray-100 rounded-lg">
              <div className="p-5 bg-primary-content text-primary rounded-xl text-xl"><Phone /></div>
              <div>
                <h1 className="font-bold text-slate-600">TELEFON</h1>
                <span className="font-semibold">123123123</span>
              </div>
            </div>
            <div className="flex gap-3 items-center p-4 text-x bg-gray-50 hover:bg-gray-100 rounded-lg">
              <div className="p-5 bg-primary-content text-primary rounded-xl text-xl"><AtSign /></div>
              <div>
                <h1 className="font-bold text-slate-600">E-POSTA</h1>
                <span className="font-semibold">demo@email.com</span>
              </div>
            </div>
            <div className="flex gap-3 items-center p-4 text-x bg-gray-50 hover:bg-gray-100 rounded-lg">
              <div className="p-5 bg-primary-content text-primary rounded-xl text-xl"><MapPin /></div>
              <div>
                <h1 className="font-bold text-slate-600">ADRES</h1>
                <span className="font-semibold">İstanbul, Türkiye</span>
              </div>
            </div>
          </div>
          <div className="flex flex-col gap-3 p-6 mt-6 bg-white rounded-xl border-1 border-gray-300">
            <div className="flex items-center text-xl border-b-1 border-b-gray-300 pb-3">
              <Share2 className="inline-block mr-2" />
              <span className="font-semibold">Sosyal Medya</span>
            </div>
            <div className="grid grid-cols-2 mt-4 gap-3">
              <Link to={'#'} className="col-span-1 flex justify-center items-center py-2 rounded-full bg-gray-100 border-1 border-gray-300 hover:bg-gray-200 transition-all duration-300 text-gray-700">
                <Instagram width={20} className="inline-block mr-2" />
                Instagram
              </Link>
              <Link to={'#'} className="col-span-1 flex justify-center items-center py-2 rounded-full bg-gray-100 border-1 border-gray-300 hover:bg-gray-200 transition-all duration-300 text-gray-700">
                <Facebook width={20} className="inline-block mr-2 text-xl" />
                Facebook
              </Link>
              <Link to={'#'} className="col-span-1 flex justify-center items-center py-2 rounded-full bg-gray-100 border-1 border-gray-300 hover:bg-gray-200 transition-all duration-300 text-gray-700">
                <Twitter width={20} className="inline-block mr-2 text-xl" />
                Twitter/X
              </Link>
              <Link to={'#'} className="col-span-1 flex justify-center items-center py-2 rounded-full bg-gray-100 border-1 border-gray-300 hover:bg-gray-200 transition-all duration-300 text-gray-700">
                <Linkedin width={20} className="inline-block mr-2 text-xl" />
                Linkedin
              </Link>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
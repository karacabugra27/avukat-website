import { useMemo, useState } from "react";
import { Link } from 'react-router-dom';
import { ArrowLeft, ArrowRight } from "lucide-react";
import toast from "react-hot-toast";

// Çok adımlı (wizard) Randevu Formu — React + Tailwind
// Adımlar: 1) Kişisel Bilgiler, 2) Zaman, 3) Hizmet, 4) Ödeme & Not, 5) Önizleme & Onay
// Not: Submit'te console.log ve alert çalışır. Backend'e bağlamak için fetch/axios ile POST edin.

const CALISANLAR = [
  { id: 1, ad: "Ayşe Yılmaz" },
  { id: 2, ad: "Mehmet Demir" },
  { id: 3, ad: "Selin Arslan" },
];

const STEPS = [
  { key: "kisisel", title: "Kişisel Bilgiler" },
  { key: "avukat", title: "Avukat" },
  { key: "tarih", title: "Tarih" },
  { key: "onizleme", title: "Önizleme" },
];

// 30 dakika aralıklarla saat seçenekleri
const SAAT_SECENEKLERI = [
  "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
  "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
  "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
  "18:00", "18:30", "19:00"
];

export default function RandevuAl() {
  const [step, setStep] = useState(0);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [form, setForm] = useState({
    ad: "",
    soyad: "",
    telefon: "",
    tarih: "",
    saat: "",
    calisanId: 0,
    not: "",
  });

  const calisan = useMemo(() => CALISANLAR.find((c) => String(c.id) === String(form.calisanId)), [form.calisanId]);

  // Bugünün tarihini al (geçmiş tarih seçimini engellemek için)
  const today = new Date().toISOString().split('T')[0];

  const progress = Math.round(((step + 1) / STEPS.length) * 100);

  function validateCurrentStep() {
    // Basit doğrulamalar
    if (step === 0) {
      if (!form.ad?.trim() || !form.soyad?.trim() || !form.telefon?.trim()) return "Lütfen bilgilerin tamamını giriniz.";
    }
    if (step === 1) {
      if (!form.calisanId) return "Lütfen avukat seçiniz.";
    }
    if (step === 2) {
      if (!form.tarih || !form.saat) return "Lütfen tarih ve saat seçiniz.";
    }
    return null;
  }

  function handleChange(name: keyof typeof form, value: any) {
    setForm(prev => ({...prev, [name]: value}));
  }

  function next() {
    const validate = validateCurrentStep();
    if(validate !== null) {
      toast.error(validate);
      return;
    }
    setStep((s) => Math.min(s + 1, STEPS.length - 1));
  }

  function back() {
    setStep((s) => Math.max(s - 1, 0));
  }

  function resetAll() {
    setForm({
      ad: "",
      soyad: "",
      telefon: "",
      tarih: "",
      saat: "",
      calisanId: 0,
      not: "",
    });
    setStep(0);
  }

  function handleSubmit(e: React.FormEvent) {
    e?.preventDefault?.();
  }

  return (
    <>
      <div style={{backgroundImage: "url(/low-poly-grid-haikei.png)", backgroundRepeat: "repeat"}} className="min-h-screen w-full pt-22">
        <div className="relative z-10 py-10 px-4 sm:px-6 lg:px-8">
          <div className="mx-auto max-w-3xl">
            {/* Header */}
            <header className="mb-6">
              <div className="flex items-center gap-4 mb-4">
                <Link to="/" className="flex items-center gap-2 hover:underline">
                  <ArrowLeft width={18} />
                  <span className="text-sm font-medium">Ana Sayfa</span>
                </Link>
              </div>
              <h1 className="text-3xl sm:text-4xl font-semibold tracking-tight">Randevu Al</h1>
              <p className="mt-2 text-lg">Bilgileri girin ve randevunuzu oluşturun.</p>
            </header>

            {/* Stepper */}
            <div className="mb-6 flex">
              <div className="flex items-center gap-4 text-sm font-medium">
                {STEPS.map((s, i) => (
                  <div key={s.key} className="flex-1 flex items-center">
                    <div className={`h-9 px-3 pt-2 rounded border ${i === step ? "bg-primary text-white border-primary font-semibold" : i < step ? "bg-primary/20 text-gray-600 border-primary/50" : ""} whitespace-nowrap overflow-hidden text-ellipsis backdrop-blur-sm`}>{i + 1}. {s.title}</div>
                  </div>
                ))}
              </div>
            </div>

            {/* Card */}
            <form onSubmit={handleSubmit} className="bg-white/95 backdrop-blur-sm rounded shadow-xl border border-white/20 p-6">
              {/* STEP 1 */}
              {step === 0 && (
                <section className="space-y-4">
                  <h2 className="text-xl font-semibold text-stone-800 mb-2">Kişisel Bilgiler</h2>
                  <p>Kişisel bilgilerinizi giriniz.</p>
                  <div>
                    <div className="flex flex-col gap-2 mb-3">
                      <label htmlFor="ad" className="text-sm font-medium text-stone-700">Ad</label>
                      <input id="ad" type="text" placeholder="Ad"
                        value={form.ad}
                        onChange={(e) => handleChange("ad", e.target.value)}
                        className="h-12 px-4 bg-white rounded border border-stone-300 focus:outline-none focus:ring-1 focus:ring-primary transition-all" />
                    </div>
                    <div className="flex flex-col gap-2 mb-3">
                      <label htmlFor="soyad" className="text-sm font-medium text-stone-700">Soyad</label>
                      <input id="soyad" type="text" placeholder="Soyad"
                        value={form.soyad}
                        onChange={(e) => handleChange("soyad", e.target.value)}
                        className="h-12 px-4 bg-white rounded border border-stone-300 focus:outline-none focus:ring-1 focus:ring-primary transition-all" />
                    </div>
                    <div className="flex flex-col gap-2 mb-3">
                      <label htmlFor="phone" className="text-sm font-medium text-stone-700">Telefon</label>
                      <input id="phone" type="text" placeholder="Telefon"
                        value={form.telefon}
                        onChange={(e) => handleChange("telefon", e.target.value)}
                        className="h-12 px-4 bg-white rounded border border-stone-300 focus:outline-none focus:ring-1 focus:ring-primary transition-all" />
                    </div>
                  </div>
                </section>
              )}

              {/* STEP 2 */}
              {step === 1 && (
                <section className="space-y-4">
                  <h2 className="text-xl font-semibold text-stone-800 mb-2">Avukat Seçimi</h2>
                  <p>Randevu almak istediğiniz avukatımızı seçiniz.</p>
                  <div>
                    <div className="flex flex-col gap-2">
                      <label htmlFor="calisanId" className="text-sm font-medium text-stone-700">Avukat</label>
                      <select id="calisanId" value={form.calisanId}
                        onChange={(e) => handleChange("calisanId", e.target.value)}
                        className="h-12 px-4 bg-white rounded border border-stone-300 focus:outline-none focus:ring-1 focus:ring-primary transition-all">
                        <option value="0" disabled>Avukat</option>
                        {CALISANLAR.map((c) => (<option key={c.id} value={c.id}>{c.ad}</option>))}
                      </select>
                    </div>
                  </div>
                </section>
              )}

              {/* STEP 3 */}
              {step === 2 && (
                <section className="space-y-4">
                  <h2 className="text-xl font-semibold text-stone-800 mb-2">Randevu Zamanı</h2>
                  <p>Randevu zamanını seçin.<br/><span className="text-gray-500">(Seçtiğiniz avukata göre uygun tarih ve saatler değişiklik gösterebilir.)</span></p>
                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    <div className="flex flex-col gap-2">
                      <label htmlFor="tarih" className="text-sm font-medium text-stone-700">Tarih</label>
                      <input id="tarih" type="date" min={today}
                        value={form.tarih}
                        onChange={(e) => handleChange("tarih", e.target.value)}
                        className="h-12 px-4 bg-white rounded border border-stone-300 focus:outline-none focus:ring-1 focus:ring-primary transition-all" />
                    </div>
                    <div className="flex flex-col gap-2">
                      <label htmlFor="saat" className="text-sm font-medium text-stone-700">Saat</label>
                      <select id="saat"
                        value={form.saat}
                        onChange={(e) => handleChange("saat", e.target.value)}
                        className="h-12 px-4 bg-white rounded border border-stone-300 focus:outline-none focus:ring-1 focus:ring-primary transition-all">
                        <option value="" disabled>Saat</option>
                        {SAAT_SECENEKLERI.map((saat) => (
                          <option key={saat} value={saat}>{saat}</option>
                        ))}
                      </select>
                    </div>
                  </div>
                </section>
              )}

              {/* STEP 5 */}
              {step === 3 && (
                <section className="space-y-4">
                  <h2 className="text-xl font-semibold text-stone-800 mb-4">Önizleme & Onay</h2>
                  <div className="rounded-xl border border-gray-300 p-6">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                      <div className="space-y-3">
                        <div className="flex flex-col">
                          <span className="text-xs font-medium text-stone-500 uppercase tracking-wide">Ad Soyad</span>
                          <span className="text-lg font-semibold text-stone-800">{form.ad || "-"} {form.soyad || ""}</span>
                        </div>
                        <div className="flex flex-col">
                          <span className="text-xs font-medium text-stone-500 uppercase tracking-wide">Tarih</span>
                          <span className="text-lg font-semibold text-stone-800">{form.tarih || "-"}</span>
                        </div>
                        <div className="flex flex-col">
                          <span className="text-xs font-medium text-stone-500 uppercase tracking-wide">Avukat</span>
                          <span className="text-lg font-semibold text-stone-800">{calisan?.ad || "-"}</span>
                        </div>
                      </div>
                      <div className="space-y-3">
                        <div className="flex flex-col">
                          <span className="text-xs font-medium text-stone-500 uppercase tracking-wide">Telefon</span>
                          <span className="text-lg font-semibold text-stone-800">{form.telefon || "-"}</span>
                        </div>
                        <div className="flex flex-col">
                          <span className="text-xs font-medium text-stone-500 uppercase tracking-wide">Saat</span>
                          <span className="text-lg font-semibold text-stone-800">{form.saat || "-"}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <p className="text-xs text-stone-500">Bilgileri kontrol edin. Gerekirse geri dönüp düzenleyebilirsiniz.</p>
                </section>
              )}

              {/* Actions */}
              <div className="mt-6 flex items-center justify-between">
                <button type="button" onClick={resetAll} className="btn">Temizle</button>
                <div className="flex items-center gap-3">
                  <button type="button" onClick={back} disabled={step === 0} className={`${step !== 0 && "hover:cursor-pointer"} btn`}><ArrowLeft width={18}/></button>
                  {step < STEPS.length - 1 ? (
                    <button type="button" onClick={next} className="btn bg-primary text-white"><ArrowRight width={18}/></button>
                  ) : (
                    <button type="submit" disabled={isSubmitting} className="btn bg-primary text-white">{isSubmitting ? "Gönderiliyor..." : "Onayla & Gönder"}</button>
                  )}
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
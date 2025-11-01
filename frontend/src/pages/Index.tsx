import { useState, useEffect } from "react";
import axios from "axios";
import { Check, Users, CalendarDays, DollarSign, Video, ArrowUp } from "lucide-react";
import { motion, type Variants } from "framer-motion";
import LawyerCard from "../components/site/LawyerCard";
import FaqCard from "../components/site/FaqCard";
import type { Lawyer, Faq } from "../types";

const fadeInUp: Variants = {
  hidden: { opacity: 0, y: 40 },
  visible: {
    opacity: 1,
    y: 0,
    transition: { duration: 0.6, ease: "easeOut" as const }
  }
};

const fadeStagger: Variants = {
  hidden: { opacity: 0, y: 24 },
  visible: (index: number = 0) => ({
    opacity: 1,
    y: 0,
    transition: { duration: 0.5, ease: "easeOut" as const, delay: index * 0.1 }
  })
};

export default function Index() {
  const [lawyers, setLawyers] = useState<Lawyer[]>([]);
  const [faqs, setFaqs] = useState<Faq[]>([]);
  const [showScrollTop, setShowScrollTop] = useState(false);
  const [scrollProgress, setScrollProgress] = useState(0);

  async function getLawyers() {
    try {
      const response = await axios.get('http://localhost:8080/api/lawyers');
      if (response.status === 200) setLawyers(response.data);
    }
    catch (error: any) {
      console.log(error);
    }
  }

  async function getFaqs() {
    try {
      const response = await axios.get('http://localhost:8080/api/faq');
      if (response.status === 200) setFaqs(response.data);
    }
    catch (error: any) {
      console.log(error);
    }
  }

  useEffect(() => {
    getLawyers();
    getFaqs();
  }, [])

  // Scroll izleme
  useEffect(() => {
    const updateScrollState = () => {
      const scrollTop = window.scrollY;
      const maxScrollable = document.documentElement.scrollHeight - window.innerHeight;
      const progress = maxScrollable > 0 ? Math.min((scrollTop / maxScrollable) * 100, 100) : 0;

      setScrollProgress(progress);
      setShowScrollTop(scrollTop > 300);
    };

    updateScrollState();
    window.addEventListener("scroll", updateScrollState, { passive: true });
    window.addEventListener("resize", updateScrollState);
    return () => {
      window.removeEventListener("scroll", updateScrollState);
      window.removeEventListener("resize", updateScrollState);
    };
  }, []);

  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    <>
      {/* Hero with bg image */}
      <div
        id="index"
        className="hero min-h-screen"
        style={{
          backgroundImage: "url(/76-scaled.jpg)"
        }}
      >
        <div className="hero-overlay bg-black/60"></div>
        <div className="hero-content text-neutral-content text-center">
          <motion.div
            className="max-w-xl"
            variants={fadeInUp}
            initial="hidden"
            animate="visible"
          >
            <h1 className="mb-5 text-5xl font-bold text-[var(--color-primary)]">Lorem Ipsum</h1>
            <p className="mb-5 text-white">
              Provident cupiditate voluptatem et in. Quaerat fugiat ut assumenda excepturi exercitationem
              quasi. In deleniti eaque aut repudiandae et a id nisi.
            </p>
            <button className="btn btn-primary">Get Started</button>
          </motion.div>
        </div>
      </div>
      {/* Hero */}
      <div className="hero bg-base-200 min-h-[400px]">
        <div className="hero-content text-center">
          <motion.div
            variants={fadeInUp}
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true, amount: 0.2 }}
          >
            <span className="text-3xl border-b-1 border-gray-600 px-5 font-semibold text-gray-500">Hukuk Bürosu</span>
            <h1 className="text-4xl mt-3 text-gray-600">Online Hukuki Danışmanlık</h1>
            <h2 className="text-lg mt-5 text-gray-500">Online Avukat Hizmeti ile Hızlı ve Güvenli Hukuki Danışmanlık</h2>
            <p className="text-gray-500">
              Online hukuki danışmanlık sistemimiz, internet bağlantınızın olduğu her yerden bilgisayar, tablet veya cep telefonu aracılığıyla uzman avukatlarla görüntülü görüşme yapabileceğiniz güvenli ve kullanıcı dostu bir platformdur
            </p>
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-x-6 mt-6">
              <motion.div
                className="col-span-1 flex p-4 hover:shadow-xl transition-all duration-500"
                variants={fadeStagger}
                initial="hidden"
                whileInView="visible"
                viewport={{ once: true, amount: 0.2 }}
                custom={0}
              >
                <div className="bg-[var(--color-primary)]/20 text-[var(--color-primary)] rounded-lg p-4">
                  <Check />
                </div>
                <p className="text-gray-500">Yüz yüze görüşme zorunluluğunu ortadan kaldıran bu sistem sayesinde, hukuki destek almak artık çok daha kolay.</p>
              </motion.div>
              <motion.div
                className="col-span-1 flex p-4 hover:shadow-2xl transition-all duration-500"
                variants={fadeStagger}
                initial="hidden"
                whileInView="visible"
                viewport={{ once: true, amount: 0.2 }}
                custom={1}
              >
                <div className="bg-[var(--color-primary)]/20 text-[var(--color-primary)] rounded-lg p-4">
                  <Check />
                </div>
                <p className="text-gray-500">Online avukat hizmeti ile zamandan tasarruf eder, hukuki sürecinizi güvenle yönetirsiniz.</p>
              </motion.div>
            </div>
          </motion.div>
        </div>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 bg-gray-100 px-6 lg:px-24 py-12 border-b-2 border-slate-100">
        <motion.div
          className="col-span-1 md:col-span-2 lg:col-span-4"
          variants={fadeInUp}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.2 }}
        >
          <h1 className="text-4xl text-center text-gray-600">Online Randevu Nasıl Oluşturulur</h1>
        </motion.div>
        <motion.div
          className="flex flex-col items-center justify-center gap-3 bg-white p-8 rounded-xl shadow-sm"
          variants={fadeStagger}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.2 }}
          custom={0}
        >
          <div className="bg-[var(--color-primary)]/20 text-[var(--color-primary)] rounded-xl p-4">
            <Users />
          </div>
          <h1 className="text-xl font-semibold text-gray-600">Avukatını Seç</h1>
          <p className="text-center text-lg text-gray-500">Sistemimizdeki avukatların çalışma ve uzmanlık alanlarını inceleyerek size en uygun olan avukatı seçebilirsiniz</p>
        </motion.div>
        <motion.div
          className="flex flex-col items-center justify-center gap-3 bg-white p-8 rounded-xl shadow-sm"
          variants={fadeStagger}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.2 }}
          custom={1}
        >
          <div className="bg-[var(--color-primary)]/20 text-[var(--color-primary)] rounded-xl p-4">
            <CalendarDays />
          </div>
          <h1 className="text-xl font-semibold text-center text-gray-600">Görüşmeni Planla</h1>
          <p className="text-center text-lg text-gray-500">Görüntülü ya da sesli olarak görüşmeye hemen başlayabilir veya size en uygun tarihe randevu oluşturabilirsiniz</p>
        </motion.div>
        <motion.div
          className="flex flex-col items-center justify-center gap-3 bg-white p-8 rounded-xl shadow-sm"
          variants={fadeStagger}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.2 }}
          custom={2}
        >
          <div className="bg-[var(--color-primary)]/20 text-[var(--color-primary)] rounded-xl p-4">
            <DollarSign />
          </div>
          <h1 className="text-xl font-semibold text-center text-gray-600">Ödemeni Tamamla</h1>
          <p className="text-center text-lg text-gray-500">Online olarak banka kartı ya da kredi kartı ile güvenli ve hızlı bir şekilde ödemenizi tamamlayabilirsiniz</p>
        </motion.div>
        <motion.div
          className="flex flex-col items-center justify-center gap-3 bg-white p-8 rounded-xl shadow-sm"
          variants={fadeStagger}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.2 }}
          custom={3}
        >
          <div className="bg-[var(--color-primary)]/20 text-[var(--color-primary)] rounded-xl p-4">
            <Video />
          </div>
          <h1 className="text-xl font-semibold text-center text-gray-600">Görüşmeni Yap</h1>
          <p className="text-center text-lg text-gray-500">Randevu saatinizde SMS yoluyla tarafınıza gelen link üzerinden sisteme giriş yaparak görüşmenizi gerçekleştirebilirsiniz</p>
        </motion.div>
      </div>
      {lawyers.length > 0 && (
        <div id="avukatlar" className="px-6 lg:px-24 py-12">
          <h1 className="text-4xl">Avukatlar</h1>
          <div className="grid grid-cols-1 gap-y-6 mt-6">
            {lawyers.map((lawyer, index) => (
              <motion.div
                key={lawyer.id}
                variants={fadeStagger}
                initial="hidden"
                whileInView="visible"
                viewport={{ once: true, amount: 0.2 }}
                custom={index}
              >
                <LawyerCard lawyer={lawyer} />
              </motion.div>
            ))}
          </div>
        </div>
      )}
      {faqs.length > 0 && (
        <div id="sss" style={{backgroundImage: "url(/low-poly-grid-haikei.png)", backgroundRepeat: "repeat"}} className="px-6 lg:px-24 py-12 bg-gray-100">
          <h1 className="text-4xl">Sık Sorulanlar</h1>
          <div className="grid grid-cols-1 gap-y-3 mt-6">
            {faqs.map((faq, index) => (
              <motion.div
                key={faq.id}
                variants={fadeStagger}
                initial="hidden"
                whileInView="visible"
                viewport={{ once: true, amount: 0.2 }}
                custom={index}
              >
                <FaqCard faq={faq} />
              </motion.div>
            ))}
          </div>
        </div>
      )}

      {/* Scroll to top butonu */}
      {showScrollTop && (
        <motion.div
          className="fixed bottom-6 right-6 p-[3px] rounded-full shadow-lg transition-all duration-300 z-50"
          style={{
            background: `conic-gradient(#ffffff ${scrollProgress}%, rgba(226, 232, 240, 0.6) ${scrollProgress}% 100%)`
          }}
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ duration: 0.3, ease: "easeOut" }}
        >
          <button
            onClick={scrollToTop}
            className="flex items-center justify-center bg-[var(--color-primary)] text-white p-3 rounded-full hover:cursor-pointer hover:bg-[var(--color-primary)]/80 transition-colors duration-300"
            aria-label="Sayfanın başına dön"
          >
            <ArrowUp size={22} />
          </button>
        </motion.div>
      )}
    </>
  );
}

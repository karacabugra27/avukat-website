import type { Faq } from "../types";
import { MessageCircleQuestionMark } from "lucide-react";

export default function FaqCard({ faq, ...props }: { faq: Faq }) {
  return (
    <div tabIndex={0} className="collapse collapse-arrow bg-white border-1 border-gray-200 hover:shadow-md transition-all duration-300">
      <div className="collapse-title font-semibold text-xl text-[var(--color-primary)]">
        <MessageCircleQuestionMark size={32} strokeWidth={1.5} className="inline-block mr-4"/>
        {faq.question}
      </div>
      <div className="collapse-content text-md">
        <p>{faq.answer}</p>
      </div>
    </div>
  );
}
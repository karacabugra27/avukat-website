import type { Lawyer } from "../types";
import { UserRound } from "lucide-react";
import { useState, useEffect } from "react";
import axios from "axios";

import { btnPrimary, btnSecondary } from "../styles";
import type { Experience } from "../types";

export default function LawyerCard({ lawyer, ...props }: { lawyer: Lawyer }) {
  const [experiences, setExperiences] = useState<Experience[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  async function getExperience() {
    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/api/experience/lawyer/${lawyer.id}`);
      if (response.status === 200) setExperiences(response.data);
    }
    catch (error: any) {
      console.log(error);
      setError(true);
    }
    finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    getExperience();
  }, [])

  return (
    <div className="flex flex-col sm:flex-row justify-between w-full rounded-lg border-1 border-gray-200 p-6 hover:bg-gray-50 hover:shadow-md transition-all duration-400">
      <div className="flex flex-col sm:flex-row gap-x-8">
        <div className="flex justify-center p-12 bg-gray-200 rounded-lg">
          <UserRound size={48} strokeWidth={1} />
        </div>
        <div>
          <h1 className="text-2xl">{`${lawyer.firstName} ${lawyer.lastName}`}</h1>
          <h1 className="text-md text-gray-500">Avukat</h1>
          <div className="flex flex-wrap gap-3 mt-6">
            {loading ? (
              <h1>Yükleniyor...</h1>
            ) : (
              error ? (
                <h1>Hata oluştu.</h1>
              ) : (
                experiences.map(exp => {
                  return (
                    <div
                      key={exp.id}
                      className="px-2 py-1 mx-auto bg-[var(--color-primary)]/20 text-[var(--color-primary)] text-xs border-1 border-[var(--color-primary)] rounded">
                      {exp.title}
                    </div>
                  );
                })
              )
            )}
          </div>
        </div>
      </div>
      <div className="flex flex-col justify-center gap-3 py-6">
        <button className={btnPrimary}>Hakkında</button>
        <button className={btnSecondary}>Randevu Al</button>
      </div>
    </div>
  );
}
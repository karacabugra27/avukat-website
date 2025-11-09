import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import type { Lawyer } from "../../../types";
import { emptyLawyer } from "../../../types";

export default function LawyerIndex() {
  const navigate = useNavigate();
  const [lawyer, setLawyer] = useState<Lawyer>(emptyLawyer());
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  async function getLawyer() {
    setLoading(true);
    setTimeout(async () => {
      try {
        const email = localStorage.getItem("email");
        const response = await axios.get(`http://localhost:8080/api/lawyer/${email}`);
        if (response.status === 200) {
          if (response.data.firstName === null || response.data.firstName === "") {
            //create account
          }
          else {
            setLawyer(response.data);
          }
        }
      }
      catch (error: any) {
        console.log(error);
        setError(true);
      }
      finally {
        setLoading(false);
      }
    }, 5000);
  }

  useEffect(() => {
    getLawyer();
  }, []);

  if (loading) {
    return <h1 className="text-2xl font-bold">Yükleniyor...</h1>
  }

  if (error) {
    return <h1 className="text-2xl font-bold">Bir hata oluştu.</h1>;
  }

  return (
    <h1>Avukat Dashboard</h1>
  );
}
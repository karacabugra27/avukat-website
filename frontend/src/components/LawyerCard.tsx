import type { Lawyer } from "../types";
import { UserRound } from "lucide-react";

export default function LawyerCard({ lawyer, ...props }: { lawyer: Lawyer }) {
  return (
    <div className="flex w-full">
      <div className="p-12 bg-gray-200 rounded-lg">
        <UserRound />
      </div>
      <div>
        <h1 className="text-lg">{`${lawyer.firstName} ${lawyer.lastName}`}</h1>
      </div>
    </div>
  );
}
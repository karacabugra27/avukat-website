export interface Lawyer {
    id: number,
    bio: string,
    email: string,
    firstName: string,
    lastName: string
}

export interface Experience {
    id: number,
    lawyerId: number,
    description: string,
    title: string
}

export interface LawyerSchedule {
    id: number,
    lawyerId: number,
    dayOfWeek: number,
    dayOff: boolean,
    endTime: Date,
    startTime: Date
}

export interface Appointment {
    id: number,
    appointmentDate: Date,
    endTime: Date,
    startTime: Date,
    lawyerSchedule: number,
    clientFirstName: string,
    clientLastName: string,
    clientPhone: string
}

export interface Faq {
    id: number,
    question: string,
    answer: string
}

export interface Membership {
    id: number,
    lawyerId: number,
    endDate: Date,
    startDate: Date,
    name: string
}
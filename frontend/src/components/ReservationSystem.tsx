import React, {useCallback, useState} from 'react'
import {ClientForm} from "./ClientForm.tsx";
import {ReservationSlots} from "./ReservationSlots.tsx";

interface ClientData {
    name: string;
    email: string;
    phone: string;
}

interface Slot {
    id: number;
    date: string;
    time: string;
    available: boolean;
}

export const ReservationSystem: React.FC = () => {
    const [clientData, setClientData] = useState<ClientData | null>(null);
    const [reservationStatus, setReservationStatus] = useState<string | null>(null);
    const [reload, setReaload] = useState<boolean>(false);

    const handleClientSubmit = (data: ClientData) => {
        setClientData(data);
    };

    const handleReservation = async (slot: Slot) => {

        if (!clientData || slot.date === '' || slot.time === '' || clientData.phone === null ) return;

        const body = {
            clientName: clientData.name,
            clientEmail: clientData.email,
            clientPhoneNumber: clientData.phone,
            scheduleDate: slot.date,
            scheduleTime: slot.time,
        };
        try {
            const response = await fetch('http://localhost:8080/reservations/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(body),
            });

            if (!response.ok) {
                throw new Error('Error al realizar la reserva.');
            }

            const result = await response.json();
            console.log(result)
            setReservationStatus('Reserva realizada con éxito: ' + result.id);
            setReaload(true);
        } catch (error) {
            console.log(error);
            setReservationStatus('Error al realizar la reserva. Por favor, intente de nuevo.');
            setReaload(false);
        }
    };

    const realoadComplete = useCallback(() => {
        setReaload(false);
    }, []);

    return (
        <div className="container mt-5">
            <h1 className="mb-4">Sistema de Reservaciones</h1>
            {!clientData ? (
                <ClientForm onSubmit={handleClientSubmit} />
            ) : (
                <ReservationSlots clientData={clientData} onReserve={handleReservation} reload={reload} reloadComplete={realoadComplete} />
            )}
            {reservationStatus && (
                <div className={`alert ${reservationStatus.includes('éxito') ? 'alert-success' : 'alert-danger'} mt-3`}>
                    {reservationStatus}
                </div>
            )}
        </div>
    );
};

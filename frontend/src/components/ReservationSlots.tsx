import {useEffect, useState} from "react";
import {format, parseISO} from "date-fns";
import {es} from "date-fns/locale";

interface Slot {
    id: number;
    date: string;
    time: string;
    available: boolean;
}

interface FormData {
    name: string;
    email: string;
    phone: string;
}

interface ReservationSlotProps {
    clientData: FormData
    onReserve: (slot: Slot) => void
    reload: boolean
    reloadComplete: () => void
}

export const ReservationSlots: React.FC<ReservationSlotProps> = ({onReserve, reload, reloadComplete}) => {
    const API_URL = "http://localhost:8080/slots/available";

    const [slots, setSlots] = useState<Slot[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {

            const getAvailableSlots = async () => {
                try {
                    const response = await fetch(API_URL);
                    if (!response.ok) {
                        throw new Error('Error al obtener las fechas disponibles');
                    }
                    const data = await response.json();
                    setSlots(data);
                    setIsLoading(false);
                    if (reload) {
                        reloadComplete();
                    }
                } catch (error) {
                    console.error(error);
                    setError('Error al obtener las fechas disponibles');
                    setIsLoading(false);
                }
            }
            getAvailableSlots();
        }, [reload, reloadComplete])

    const formatDate = (dateString: string) => {
        const date = parseISO(dateString);
        return format(date, 'EEEE d \'de\' MMMM \'de\' yyyy', {locale: es});
    };

    return (
        <div className='container mt-5'>
            <h2 className="mb-4">Fechas disponibles</h2>

            {isLoading && <p>Cargando...</p>}

            {error && <p className='text-danger'>Error al obtener las fechas disponibles</p>}

            {slots.length === 0 ? (
                <p>No hay fechas disponibles</p>
            ) : (
                <div className='row row-cols-3 g-4'>
                    {slots.map((slot) => (
                        <div key={slot.id} className='col'>
                            <div className={'card h-100 border-success'}>
                                <div className='card-body'>
                                    <h5 className='card-title'>{formatDate(slot.date)}</h5>
                                    <p className='card-text'>Hora: {slot.time}</p>
                                    <p className='card-text'>
                                        Estado:
                                        <span> {'Disponible'}</span>
                                    </p>
                                    <button
                                        onClick={() => onReserve(slot)}
                                        className='btn btn-primary'>
                                        Reservar
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    )
}

import {format, parseISO} from 'date-fns';
import {es} from 'date-fns/locale';
import React, {useEffect, useState} from "react";

interface Slot {
    id: number;
    date: string;
    time: string;
    available: boolean;
}

export const UpdateReservation: React.FC = () => {
    const [reservationId, setReservationId] = useState('');
    const [slots, setSlots] = useState<Slot[]>([]);
    const [selectedSlot, setSelectedSlot] = useState<Slot | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [updateStatus, setUpdateStatus] = useState<string | null>(null);

    useEffect(() => {
        const getAvailableSlots = async () => {
            setLoading(true);
            try {
                const response = await fetch('http://localhost:8080/slots/available');
                if (!response.ok) {
                    throw new Error('Failed to fetch available slots');
                }
                const data = await response.json();
                setSlots(data);
            } catch (error) {
                console.log(error);
                setError('Error al cargar los slots disponibles. Por favor, intente de nuevo.');
            } finally {
                setLoading(false);
            }
        };

        getAvailableSlots();
    }, []);

    const handleReservationIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setReservationId(e.target.value);
    };

    const handleSlotSelect = (slot: Slot) => {
        setSelectedSlot(slot);
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!selectedSlot) {
            setError('Por favor, seleccione un nuevo slot para la reserva.');
            return;
        }

        setLoading(true);
        setError(null);
        setUpdateStatus(null);

        try {
            const response = await fetch(`http://localhost:8080/reservations/${reservationId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    schedule: {
                        date: selectedSlot.date,
                        time: selectedSlot.time,
                    },
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to update reservation');
            }

            setUpdateStatus('Reserva actualizada con éxito');
            setReservationId('');
            setSelectedSlot(null);
        } catch (error) {
            setError('Error al actualizar la reserva. Por favor, intente de nuevo.');
        } finally {
            setLoading(false);
        }
    };

    const formatDate = (dateString: string) => {
        const date = parseISO(dateString);
        return format(date, "EEEE d 'de' MMMM 'de' yyyy", {locale: es});
    };

    return (
        <div className='container mt-5'>
            <h1 className='mb-4'>Actualizar Reservación</h1>
            <form onSubmit={handleSubmit} className="d-flex flex-column align-items-center">
                <div className='mb-3'>
                    <label className='form-label'>ID de la Reserva</label>
                    <input
                        type='text'
                        className='form-control'
                        id='reservationId'
                        value={reservationId}
                        onChange={handleReservationIdChange}
                        required
                    />
                </div>
                <button type='submit' className='btn btn-primary mb-4' disabled={loading || !selectedSlot}>
                    Actualizar Reserva
                </button>
            </form>
            <h2 className='mb-3'>Slots Disponibles</h2>
            {loading && <p>Cargando slots disponibles...</p>}
            {error && <div className='alert alert-danger'>{error}</div>}

                <div className='row row-cols-3 g-4 mb-4'>

                    {slots.length === 0 && (
                        <div className='alert alert-warning'>
                            No hay slots disponibles en la fecha seleccionada.
                        </div>
                    )}

                    {slots.map((slot) => (
                        <div key={slot.id} className='w-25 mb-4'>
                            <div
                                className={`card h-100 ${selectedSlot?.id === slot.id ? 'border-primary' : ''}`}
                                onClick={() => handleSlotSelect(slot)}
                                style={{cursor: 'pointer'}}
                            >
                                <div className='card-body'>
                                    <h5 className='card-title'>{formatDate(slot.date)}</h5>
                                    <p className='card-text'>Hora: {slot.time}</p>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>

            {updateStatus && (
                <div className='alert alert-success mt-3'>
                    {updateStatus}
                </div>
            )}
        </div>
    );
};
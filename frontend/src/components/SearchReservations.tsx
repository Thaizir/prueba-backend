import React, { useState } from 'react';
import { format, parseISO } from 'date-fns';
import { es } from 'date-fns/locale';

interface Client {
    id: number;
    name: string;
    email: string;
    phoneNumber: string;
}

interface Schedule {
    id: number;
    date: string;
    time: string;
    available: boolean;
}

interface Reservation {
    id: number;
    client: Client;
    schedule: Schedule;
    status: string;
}

export const SearchReservations: React.FC = () => {
    const [searchDate, setSearchDate] = useState('');
    const [reservations, setReservations] = useState<Reservation[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchDate(e.target.value);
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!searchDate) {
            setError('Por favor, seleccione una fecha para buscar.');
            return;
        }

        setLoading(true);
        setError(null);

        try {
            const response = await fetch(`http://localhost:8080/reservations/search?date=${searchDate}`);
            if (!response.ok) {
                throw new Error('Failed to fetch reservations');
            }
            const data = await response.json();
            console.log(data)
            setReservations(data);
        } catch (error) {
            console.log(error);
            setError('Error al buscar las reservaciones. Por favor, intente de nuevo.');
        } finally {
            setLoading(false);
        }
    };

    const formatDate = (dateString: string) => {
        const date = parseISO(dateString);
        return format(date, "EEEE d 'de' MMMM 'de' yyyy", { locale: es });
    };

    const formatTime = (time: string) => {
        return time.substring(0, 5); // Remove seconds from time
    };
    return (
        <div className='container mt-5'>
            <h1 className='mb-4'>Buscar Reservaciones</h1>
            <form onSubmit={handleSubmit} className='mb-4'>
                <div className='mb-4'>
                    <div className="col-6">
                        <div className='mb-3'>
                            <label htmlFor='searchDate' className='form-label'> Fecha de Búsqueda</label>
                            <input
                                type='date'
                                className='form-control w-50    '
                                id='searchDate'
                                value={searchDate}
                                onChange={handleDateChange}
                                required
                            />
                        </div>
                    </div>
                    <div className='col-6 d-flex align-items-end'>
                        <button type='submit' className='btn btn-primary mb-3' disabled={loading}>
                            {loading ? 'Buscando...' : 'Buscar Reservaciones'}
                        </button>
                    </div>
                </div>
            </form>

            {error && (
                <div className='alert alert-danger' role='alert'>
                    {error}
                </div>
            )}

            {loading && (
                <div className='text-center'>
                    <p>Cargando reservaciones...</p>
                </div>
            )}

            {reservations.length > 0 ? (
                <div className='row row-cols-2 g-4'>
                    {reservations.map((reservation) => (
                        <div key={reservation.id} className='col'>
                            <div className='card h-100'>
                                <div className='card-body'>
                                    <h5 className='card-title'>Reservación #{reservation.id}</h5>
                                    <div className='card-text'>
                                        <p className='mb-1'>
                                            <strong>Fecha:</strong> {formatDate(reservation.schedule.date)}
                                        </p>
                                        <p className='mb-1'>
                                            <strong>Hora:</strong> {formatTime(reservation.schedule.time)}
                                        </p>
                                        <p className='mb-1'>
                                            <strong>Cliente:</strong> {reservation.client.name}
                                        </p>
                                        <p className='mb-1'>
                                            <strong>Email:</strong> {reservation.client.email}
                                        </p>
                                        <p className='mb-1'>
                                            <strong>Teléfono:</strong> {reservation.client.phoneNumber}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                !loading && searchDate && (
                    <div className='alert alert-info' role='alert'>
                        No se encontraron reservaciones para la fecha seleccionada.
                    </div>
                )
            )}
        </div>
    );
};
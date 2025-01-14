import React, {useState} from 'react';


export const DeleteReservation = () => {

    const [idToDelete, setIdToDelete] = useState('');
    const [deleteStatus, setDeleteStatus] = useState<string | null>(null);


    const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setIdToDelete(e.target.value);
    }

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const response = await fetch(`http://localhost:8080/reservations/${idToDelete}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (!response.ok) {
                throw new Error('Failed to delete reservation');
            }
            setDeleteStatus('Reserva cancelada con éxito');
            setIdToDelete('');
        } catch (error) {
            console.error(error);
            setDeleteStatus('Error al cancelar la reserva. Por favor, intente de nuevo.');
        }
    }

    return (
        <div className='container mt-5'>
            <h1 className='mb-4'>Cancelar Reservación</h1>
            <form className='d-flex flex-column align-items-center' onSubmit={handleSubmit}>
                <div className='mb-3 w-25'>
                    <input
                        type='text'
                        className='form-control'
                        id='id'
                        name='id'
                        placeholder='ID de la reserva a cancelar'
                        value={idToDelete}
                        onChange={handleOnChange}
                        required
                    />
                </div>
                <button type='submit' className='btn btn-danger'>Cancelar Reserva</button>
            </form>
            {deleteStatus && (
                <div className={`mt-3 alert ${deleteStatus.includes('éxito') ? 'alert-success' : 'alert-danger'}`}>
                    {deleteStatus}
                </div>
            )}
        </div>
    )
}

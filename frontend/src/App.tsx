import './App.css'
import {ReservationSystem} from "./components/ReservationSystem.tsx";
import {useState} from "react";
import {DeleteReservation} from "./components/DeleteReservation.tsx";
import {UpdateReservation} from "./components/UpdateReservation.tsx";
import {SearchReservations} from "./components/SearchReservations.tsx";

function App() {
    const [operation, setOperation] = useState('Reservar');

    const handleOperation = (op:string) => {
        setOperation(op);
    };

    return (
        <>
            <div className="d-flex gap-2">
                <button className="btn btn-primary" onClick={() => handleOperation('Reservar')}>Reservar</button>
                <button className="btn btn-primary" onClick={() => handleOperation('Buscar')}>Buscar</button>
                <button className="btn btn-primary" onClick={() => handleOperation('Actualizar')}>Actualizar</button>
                <button className="btn btn-primary" onClick={() => handleOperation('Eliminar')}>Eliminar</button>
            </div>

            {operation === 'Reservar' && <ReservationSystem />}
            {operation === 'Buscar' && <SearchReservations />}
            {operation === 'Actualizar' && <UpdateReservation />}
            {operation === 'Eliminar' && <DeleteReservation />}
        </>
    );
}


export default App

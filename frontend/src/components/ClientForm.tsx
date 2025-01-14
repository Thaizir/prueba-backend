import React, {useState} from 'react';

interface FormData {
    name: string;
    email: string;
    phone: string;
}

interface ClientFormProps {
    onSubmit: (data: FormData) => void;
}

export const ClientForm: React.FC<ClientFormProps> = ({onSubmit}) => {

    const [formData, setFormData] = useState<FormData>({
        name: '',
        email: '',
        phone: ''
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setFormData((prevState) => ({
            ...prevState,
            [name]: value
        }));
    }

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log(formData);
        onSubmit(formData);
    }

    return (
        <div className='container mt-5'>
            <form onSubmit={handleSubmit} className="d-flex flex-column justify-content-center align-items-center">
                <div className='mb-3 w-25'>
                    <input
                        type='text'
                        className='form-control'
                        id='name'
                        name='name'
                        placeholder='Nombre'
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="mb-3 w-25">
                    <input
                        type='email'
                        className='form-control'
                        id='email'
                        name='email'
                        placeholder='Correo Electronico'
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="mb-3 w-25">

                    <input
                        type='text'
                        className='form-control'
                        id='phone'
                        name='phone'
                        placeholder='Telefono'
                        value={formData.phone}
                        onChange={handleChange}
                        required
                        pattern="[0-9]{10}"
                    />
                </div>
                <button type='submit' className='btn btn-primary'>
                    Continuar
                </button>
            </form>
        </div>
    )
}


export default function Login() {
    return <>
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h1 className="text-2xl font-bold mb-8">JTool - CRM</h1>

            <form className="flex flex-col space-y-3 w-80 p-3">
                <label className="text-sm font-bold">Email</label>
                <input className="p-2 border border-gray-300 rounded-md" type="email" name="email" placeholder="Introduce tu email" required />

                <label className="text-sm font-bold">Contraseña</label>
                <input className="p-2 border border-gray-300 rounded-md" type="password" name="password" placeholder="Introduce tu contraseña "required />

                <button className="bg-sky-900 text-white py-2 px-4 rounded-md hover:bg-sky-500" type="submit">Iniciar Sesión</button>

                <a href="/register" className="font-bold underline">Aun no tienes cuenta? Registrarse!</a>
            </form>
        </div>
    </>
}
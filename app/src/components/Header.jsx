
export default function Header() {
    return <>
        <header className="py-5 px-3 bg-sky-900 text-lg">
            <div className="font-bold text-white flex justify-between">
                <div className="pl-16 font-extrabold">
                    <a href="/home">CRM - ADMINISTRADOR DE CLIENTES</a>
                </div>
                <div className="pr-32 space-x-4 flex items-center">
                    <div className="flex items-center mr-4">
                        <p className="font-extrabold">Bienvenido! ADMIN</p>
                    </div>
                    <div className="space-x-4">
                        <a href="/profile">PERFIL</a>
                        <a href="/logout">LOGOUT</a>
                    </div>
                </div>
            </div>
        </header>
    </>
}

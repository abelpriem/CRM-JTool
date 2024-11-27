
export default function Header() {
    return <>
        <header className="py-5 px-3 bg-sky-900 text-lg">
            <div className="font-bold text-white flex justify-between">
                <div className="pl-16 font-extrabold">
                    <h1>CRM - ADMINISTRADOR DE CLIENTES</h1>
                </div>
                <div className="pr-32 space-x-4 font-extrabold">
                    <a href="/profile">PROFILE</a>
                    <a href="">LOGOUT</a>
                </div>
            </div>
        </header>
    </>
}
export default function Login() {

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
  }

  return (
    <>
      <div className="flex items-center justify-center h-screen bg-gray-200">
        <div className="bg-white shadow-2xl rounded-2xl w-full max-w-md p-8">
          <div className="text-center mb-6">
            <h1 className="text-3xl font-bold mt-3 text-gray-800">Hukuk Bürosu</h1>
            <p className="text-gray-500">Lütfen giriş yapınız</p>
          </div>
          <form className="space-y-4" onSubmit={handleSubmit}>
            <div>
              <label className="block text-gray-700 font-medium mb-1">E-Posta</label>
              <div className="flex items-center border rounded-lg px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500">
                <input type="text" placeholder="E-Postanızı giriniz"
                  className="w-full outline-none text-gray-700" />
              </div>
            </div>
            <div>
              <label className="block text-gray-700 font-medium mb-1">Şifre</label>
              <div className="flex items-center border rounded-lg px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500">
                <input type="password" placeholder="Şifrenizi giriniz"
                  className="w-full outline-none text-gray-700" />
              </div>
            </div>

            <button type="submit"
              className="w-full bg-gray-900 text-white py-2 rounded-lg font-medium hover:bg-gray-700 hover:cursor-pointer transition mt-3">
              Giriş Yap
            </button>
          </form>
        </div>
      </div>
    </>
  );
}
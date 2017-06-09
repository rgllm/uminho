using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LoginRegisto
{
    class Utilizador
    {
        private UtilizadorDAO uDAO;

        public Utilizador()
        {
            uDAO = new UtilizadorDAO();
        }

        public void adicionaPesquisa(int id, string pesquisa)
        {
            uDAO.adicionaPesquisa(id, pesquisa);
        }

        public List<String> getPesquisas(int id)
        {
            return uDAO.getPesquisas(id);
        }

        public bool registaUtilizador(string n, string e, string p)
        {
            return uDAO.registaUtilizador(n, e, p);
        }

        //retorna o id do utilizador
        public int loginUtilizador(string mail, string pass)
        {
            return uDAO.login(mail, pass);
        }
    }
}

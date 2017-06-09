using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LoginRegisto
{
    class Proprietario
    {
        private ProprietarioDAO pDAO;

        public Proprietario()
        {
            pDAO = new ProprietarioDAO();
        }

        public bool registaProprietario(string n, string e, int t)
        {
            return pDAO.registaProprietario(n, e, t);
        }
    }
}
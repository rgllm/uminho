using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace LoginRegisto

{
    class Critica
    {
        //id do estabelecimento que o utilizador afirmou ter visitado
        int idEstabelecimento;
        CriticaDAO cDAO;

        public Critica()
        {
            cDAO = new CriticaDAO();
        }

        public void setIdEstabelecimento(int i)
        {
            idEstabelecimento = i;
        }


        public void efetuaCritica(int idU, int classificacao, string comentario)
        {
            cDAO.efetuaCritica(idEstabelecimento, idU, classificacao, comentario);
        }
    }

}

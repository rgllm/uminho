using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LoginRegisto
{
    class Pesquisa
    {
        string refeicao, localizacao;
        int criterio;
        List<Estabelecimento> resultados;
        List<Estabelecimento> estabelecimentos;
        EstabelecimentoDAO eDAO;

        public Pesquisa()
        {
            eDAO = new EstabelecimentoDAO();
        }

        public Pesquisa(string r, string l, int c)
        {
            refeicao = r;
            localizacao = l;
            criterio = c;
            resultados = new List<Estabelecimento>();
            estabelecimentos = new List<Estabelecimento>();
            eDAO = new EstabelecimentoDAO();
        }

        public SortedDictionary<int, Estabelecimento> getEstabelecimentos(string ing)
        {
            return eDAO.getEstabelecimentos(ing);
        }
    }

}

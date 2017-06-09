using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LoginRegisto
{
    class Estabelecimento
    {
        string site, codigo_postal, designacao, rede_social,morada;
        int telefone, NIF;
        float classificacao;
        HashSet<Refeicao> refeicoes;
        public Estabelecimento(string s, string cp, string des, string rs, int t, int n, float cl, HashSet<Refeicao> r,string m)
        {
            site = s;
            codigo_postal = cp;
            designacao = des;
            rede_social = rs;
            telefone = t;
            NIF = n;
            classificacao = cl;
            refeicoes = r;
            morada=m;
        }
        public void addRefeicao(Refeicao re)
        {
            refeicoes.Add(re);
        }

        public string getSite()
        {
            return site;
        }
        public void setSite(string site)
        {
            this.site = site;
        }
        public string getCodigo_postal()
        {
            return codigo_postal;
        }
        public void setCodigo_postal(string codigo_postal)
        {
            this.codigo_postal = codigo_postal;
        }
        public string getDesignacao()
        {
            return designacao;
        }
        public void setDesignacao(string designacao)
        {
            this.designacao = designacao;
        }
        public string getRede_social()
        {
            return rede_social;
        }
        public void setRede_social(string rede_social)
        {
            this.rede_social = rede_social;
        }
        public int getTelefone()
        {
            return telefone;
        }
        public void setTelefone(int telefone)
        {
            this.telefone = telefone;
        }

        public int getNIF()
        {
            return NIF;
        }
        public void setNIF(int NIF)
        {
            this.NIF = NIF;
        }
        public float getClassificacao()
        {
            return classificacao;
        }
        public void setClassificacao(float classificacao)
        {
            this.classificacao = classificacao;
        }
        public HashSet<Refeicao> getRefeicoes()
        {
            return refeicoes;
        }
        public void setRefeicoes(HashSet<Refeicao> refeicoes)
        {
            this.refeicoes = refeicoes;
        }
        public string getMorada()
        {
            return morada;
        }
    }
}

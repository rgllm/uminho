using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LoginRegisto
{
    class Refeicao
    {
        string descricao, designacao, tipo_de_cozinha, url_fotografia;
        float preco;
        HashSet<string> ingredientes;

        public Refeicao(string descricao, string designacao, string tipo_de_cozinha, string url_fotografia, HashSet<string> ingredientes)
        {
            this.descricao = descricao;
            this.designacao = designacao;
            this.tipo_de_cozinha = tipo_de_cozinha;
            this.url_fotografia = url_fotografia;
            this.ingredientes = ingredientes;
        }
        public string getDescricao()
        {
            return descricao;
        }
        public void setDescricao(string descricao)
        {
            this.descricao = descricao;
        }
        public string getDesignacao()
        {
            return designacao;
        }
        public void setDesignacao(string designacao)
        {
            this.designacao = designacao;
        }
        public string getTipo_de_cozinha()
        {
            return tipo_de_cozinha;
        }
        public void setTipo_de_cozinha(string tipo_de_cozinha)
        {
            this.tipo_de_cozinha = tipo_de_cozinha;
        }
        public string getUrl_fotografia()
        {
            return url_fotografia;
        }
        public void setUrl_fotografia(string url_fotografia)
        {
            this.url_fotografia = url_fotografia;
        }
        public float getPreco()
        {
            return preco;
        }
        public void setPreco(float preco)
        {
            this.preco = preco;
        }
        public HashSet<String> getIngredientes()
        {
            return ingredientes;
        }
        public void setIngredientes(HashSet<String> ingredientes)
        {
            this.ingredientes = ingredientes;
        }
    }
}

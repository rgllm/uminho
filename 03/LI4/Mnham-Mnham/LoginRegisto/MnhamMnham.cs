using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LoginRegisto
{
    public class MnhamMnham
    {
        public const int INICIAL = 1;
        public const int PESQUISA1 = 2;
        public const int PESQUISA2 = 3;
        public const int PESQUISA3 = 4;
        public const int ADDPREFERENCIA = 5;
        public const int ADDNAOPREFERENCIA = 6;


        SortedDictionary<float, Estabelecimento> resultPesquisa;
        Utilizador u;
        Preferencia p;
        Critica c;
        Pesquisa pesq;
        int estado;
        int idUser;

        public MnhamMnham()
        {
            u = new Utilizador();
            c = new Critica();
            pesq = new Pesquisa();
            estado = INICIAL;
        }

        public bool registaUtilizador(string nome, string mail, string pass)
        {
            return u.registaUtilizador(nome, mail, pass);
        }

        public int loginUtilizador(string email, string pass)
        {
            idUser = u.loginUtilizador(email, pass);
            p = new Preferencia(idUser);
            return idUser;
        }

        public string getResposta(string pedido)
        {
            float clAux;
            switch (estado)
            {
                case INICIAL:
                    if (pedido.Equals("pesquisa") || pedido.Equals("Apetece-me algo"))
                    {
                        estado = PESQUISA1;
                        return "O que deseja hoje?";
                        
                    }
                    if(pedido.Equals("adicionar preferencia"))
                    {
                        estado = ADDPREFERENCIA;
                        return "Introduza uma preferência que pretenda registar ou introduza \"cancelar\" para voltar atrás";
                    }
                    if (pedido.Equals("adicionar nao-preferencia"))
                    {
                        estado = ADDNAOPREFERENCIA;
                        return "Introduza uma não-preferência que pretenda registar ou introduza \"cancelar\" para voltar atrás";
                    }
                    if (pedido.Equals("ajuda"))
                        return "Estão disponíveis os seguintes comandos:\n-Pesquisa\n-Registar preferência\n*-Remover preferência\n*-Listar preferências\n*-Histórico\n (* não implementados)";
                    break;
                case PESQUISA1:
                    SortedDictionary < int, Estabelecimento > aux = pesq.getEstabelecimentos(pedido);
                     
                    if (aux.Count == 0) {
                        estado = INICIAL;
                        return "Sem resultados.";
                    }
                    //  ORDENAÇÃO DOS RESULTADOS
                    float exp = 0.0001f;
                    int cont = 1;
                    SortedDictionary<float, Estabelecimento> es = new SortedDictionary<float, Estabelecimento>();
                    foreach (Estabelecimento e1 in aux.Values)
                    {
                        float classi = e1.getClassificacao();

                        HashSet<String> ings = e1.getRefeicoes().ElementAt(0).getIngredientes();
                        int asdf = ings.Count;

                        int breakPoint = 0;

                        for (int i = 0; i < ings.Count(); i++)
                        {
                            if (p.preferencias.Contains(ings.ElementAt(i).Trim()))
                            {
                                classi += 10.0f;
                            }
                            else if (p.naoPreferencias.Contains(ings.ElementAt(i).Trim()))
                            {
                                classi = -100000000;
                            }
                        }

                        if (classi >= 0)
                        {
                            if (es.ContainsKey(classi))
                                es.Add(classi + (cont + 1) * exp, e1);
                            else
                                es.Add(classi, e1);
                        }
                    }
                    
                    resultPesquisa = es;
                    clAux = resultPesquisa.Keys.Max();
                    Estabelecimento e = resultPesquisa[clAux];
                    resultPesquisa.Remove(clAux);
                    estado = PESQUISA2;
                    return e.getDesignacao() + "\n" +
                            "Classificação: " + e.getClassificacao() + " estrelas\n" +
                            "Morada: " + e.getMorada() + "\n(introduza \"proximo\" para ver mais resultados)"; 
                case PESQUISA2:
                    
                    if (pedido.Equals("proximo"))
                    {
                        if (resultPesquisa.Count == 0)
                        {
                            estado = INICIAL;
                            return "Sem mais resultados.";
                        }
                        clAux = resultPesquisa.Keys.Max();
                        Estabelecimento e1 = resultPesquisa[clAux];
                        resultPesquisa.Remove(clAux);

                        return e1.getDesignacao() + "\n" +
                                "Classificação: " + e1.getClassificacao() + " estrelas\n" +
                                "Morada: " + e1.getMorada() + "\n";
                    }
                    estado = INICIAL;
                    return "Apresentação dos resultados cancelada.";
                case ADDPREFERENCIA:
                    if (pedido.Equals("cancelar"))
                    {
                        estado = INICIAL;
                        return "Operação cancelada";
                    }
                    p.registaPreferencia(idUser, pedido, "");
                    estado = INICIAL;
                    return "Preferência registada!";
                case ADDNAOPREFERENCIA:
                    p.registaNaoPreferencia(idUser, pedido, "");
                    estado = INICIAL;
                    return "Não-preferência registada!";

            }
            return "ERRO";
        }
    }
}

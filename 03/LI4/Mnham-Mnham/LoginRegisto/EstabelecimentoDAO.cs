using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace LoginRegisto
{
    class EstabelecimentoDAO
    {
        public EstabelecimentoDAO() { }

        //retorna o id do estabelecimento para o proprietário armazenar
        public int registaEstabelecimento(string desig, int nif, int id_prop, int tele, string rede, string site, string codigo, string mora)
        {
            Int32 id = -1;
            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "li4server1.database.windows.net";
            builder.UserID = "rgllm";
            builder.Password = "Universidade2016";
            builder.InitialCatalog = "li4";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {

                conn.Open();

                SqlParameter designacao = new SqlParameter("@designacao", System.Data.SqlDbType.NVarChar, 50);
                designacao.Value = desig;

                SqlParameter NIF = new SqlParameter("@NIF", System.Data.SqlDbType.Decimal);
                NIF.Value = nif;

                SqlParameter idProprietario = new SqlParameter("@idProprietario", System.Data.SqlDbType.Int);
                idProprietario.Value = id_prop;

                SqlParameter telefone = new SqlParameter("@telefone", System.Data.SqlDbType.Int);
                telefone.Value = tele;

                SqlParameter rede_social = new SqlParameter("@rede_social", System.Data.SqlDbType.NVarChar, 20);
                rede_social.Value = rede;

                SqlParameter url_site = new SqlParameter("@url_site", System.Data.SqlDbType.NVarChar, 30);
                url_site.Value = site;

                SqlParameter codigo_postal = new SqlParameter("@codigo_postal", System.Data.SqlDbType.NVarChar, 8);
                codigo_postal.Value = codigo;

                SqlParameter morada = new SqlParameter("@morada", System.Data.SqlDbType.NVarChar, 100);
                morada.Value = mora;


                SqlCommand registaEstabelecimento;

                registaEstabelecimento = new SqlCommand("INSERT INTO Estabelecimento ( designaçao, NIF, id_proprietario, telefone, rede_social, url_site, classificaçao, codigo_postal, morada) " +
                                         "Values (@designacao, @NIF, @idProprietario, @telefone, @rede_social, @url_site, 0.0, @codigo_postal, @morada)", conn);
                registaEstabelecimento.Parameters.Add(designacao);
                registaEstabelecimento.Parameters.Add(NIF);
                registaEstabelecimento.Parameters.Add(idProprietario);
                registaEstabelecimento.Parameters.Add(telefone);
                registaEstabelecimento.Parameters.Add(rede_social);
                registaEstabelecimento.Parameters.Add(url_site);
                registaEstabelecimento.Parameters.Add(codigo_postal);
                registaEstabelecimento.Parameters.Add(morada);

                registaEstabelecimento.ExecuteNonQuery();

                registaEstabelecimento.Parameters.Clear();

                SqlCommand getIDEstabelecimento =
                    new SqlCommand("select id_estabelecimento from Estabelecimento where designaçao=@designacao and NIF=@NIF and id_proprietario=@idProprietario and telefone=@telefone and rede_social=@rede_social "
                    + " and url_site=@url_site and classificaçao=0.0 and codigo_postal=@codigo_postal and morada=@morada", conn);
                getIDEstabelecimento.Parameters.Add(designacao);
                getIDEstabelecimento.Parameters.Add(NIF);
                getIDEstabelecimento.Parameters.Add(idProprietario);
                getIDEstabelecimento.Parameters.Add(telefone);
                getIDEstabelecimento.Parameters.Add(rede_social);
                getIDEstabelecimento.Parameters.Add(url_site);
                getIDEstabelecimento.Parameters.Add(codigo_postal);
                getIDEstabelecimento.Parameters.Add(morada);

                id = (Int32)getIDEstabelecimento.ExecuteScalar();
            }
            return id;
        }

        public int existeRefeicao(string desig, string descri, string cozinha, bool foto, SqlConnection conn)
        {
            SqlParameter designacao = new SqlParameter("@designacao", System.Data.SqlDbType.NVarChar, 40);
            designacao.Value = desig;

            SqlParameter descricao = new SqlParameter("@descricao", System.Data.SqlDbType.NVarChar, 100);
            descricao.Value = descri;

            SqlParameter tipo_de_cozinha = new SqlParameter("@tipo_de_cozinha", System.Data.SqlDbType.NVarChar, 20);
            tipo_de_cozinha.Value = cozinha;

            SqlParameter file = new SqlParameter("@file", System.Data.SqlDbType.NVarChar, 20);
            if (foto == false)
                file.Value = "falso";
            else
                file.Value = "throwaway";

            SqlCommand com = new SqlCommand("select id_refeiçao from Refeiçao where designaçao=@designacao and foto_path=@file and descriçao=@descricao and tipo_de_cozinha=@tipo_de_cozinha", conn);
            com.Parameters.Add(designacao);
            com.Parameters.Add(file);
            com.Parameters.Add(descricao);
            com.Parameters.Add(tipo_de_cozinha);

            Object id = com.ExecuteScalar();

            Int32 idRef = -1;

            if (id != null)
                idRef = (Int32)id;

            com.Parameters.Clear();

            return idRef;
        }
        public int registaRefeicao(int idE, float preco, string desig, string descri, string cozinha, bool foto)
        {

            int id;

            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "li4server1.database.windows.net";
            builder.UserID = "rgllm";
            builder.Password = "Universidade2016";
            builder.InitialCatalog = "li4";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {

                conn.Open();

                id = existeRefeicao(desig, descri, cozinha, foto, conn);

                if (id == -1 && foto == false)
                {
                    //adiciona à tabela refeição

                    SqlParameter designacao = new SqlParameter("@designacao", System.Data.SqlDbType.NVarChar, 40);
                    designacao.Value = desig;

                    SqlParameter descricao = new SqlParameter("@descricao", System.Data.SqlDbType.NVarChar, 100);
                    descricao.Value = descri;

                    SqlParameter tipo_de_cozinha = new SqlParameter("@tipo_de_cozinha", System.Data.SqlDbType.NVarChar, 20);
                    tipo_de_cozinha.Value = cozinha;

                    SqlParameter file = new SqlParameter("@file", System.Data.SqlDbType.NVarChar, 20);
                    file.Value = "falso";

                    SqlCommand registaRef = new SqlCommand("insert into Refeiçao (designaçao, descriçao, tipo_de_cozinha, foto_path) " +
                        " values(@designacao, @descricao, @tipo_de_cozinha, @file)", conn);
                    registaRef.Parameters.Add(designacao);
                    registaRef.Parameters.Add(descricao);
                    registaRef.Parameters.Add(tipo_de_cozinha);
                    registaRef.Parameters.Add(file);

                    registaRef.ExecuteNonQuery();

                    id = existeRefeicao(desig, descri, cozinha, foto, conn);

                }
                if (foto == true)
                {
                    //adiciona à tabela refeição

                    SqlParameter designacao = new SqlParameter("@designacao", System.Data.SqlDbType.NVarChar, 40);
                    designacao.Value = desig;

                    SqlParameter descricao = new SqlParameter("@descricao", System.Data.SqlDbType.NVarChar, 100);
                    descricao.Value = descri;

                    SqlParameter tipo_de_cozinha = new SqlParameter("@tipo_de_cozinha", System.Data.SqlDbType.NVarChar, 20);
                    tipo_de_cozinha.Value = cozinha;

                    SqlParameter file = new SqlParameter("@file", System.Data.SqlDbType.NVarChar, 20);
                    file.Value = "throwaway";

                    SqlCommand registaRef = new SqlCommand("insert into Refeiçao (designaçao, descriçao, tipo_de_cozinha,foto_path) " +
                        " values(@designacao, @descricao, @tipo_de_cozinha, @file)", conn);
                    registaRef.Parameters.Add(designacao);
                    registaRef.Parameters.Add(descricao);
                    registaRef.Parameters.Add(tipo_de_cozinha);
                    registaRef.Parameters.Add(file);

                    registaRef.ExecuteNonQuery();

                    registaRef.Parameters.Clear();

                    id = existeRefeicao(desig, descri, cozinha, foto, conn);

                    string path = idE + "-" + id;

                    SqlParameter foto_path = new SqlParameter("@foto_path", System.Data.SqlDbType.NVarChar, 20);
                    foto_path.Value = path;

                    SqlCommand update = new SqlCommand("update Refeiçao set foto_path=@foto_path " +
                        " where designaçao=@designacao and foto_path='throwaway' and descriçao=@descricao and tipo_de_cozinha=@tipo_de_cozinha", conn);
                    update.Parameters.Add(foto_path);
                    update.Parameters.Add(designacao);
                    update.Parameters.Add(descricao);
                    update.Parameters.Add(tipo_de_cozinha);

                    update.ExecuteNonQuery();

                    update.Parameters.Clear();
                }


                //adiciona à tabela estabelecimentoRefeicao

                SqlParameter idEstabelecimento = new SqlParameter("@idEstabelecimento", System.Data.SqlDbType.Int);
                idEstabelecimento.Value = idE;

                SqlParameter idRefeiçao = new SqlParameter("@idRefeiçao", System.Data.SqlDbType.Int);
                idRefeiçao.Value = id;

                SqlParameter preço = new SqlParameter("@preço", System.Data.SqlDbType.Decimal);
                preço.Value = preco;

                SqlCommand registaEstRef = new SqlCommand("INSERT INTO EstabelecimentoRefeiçao ( id_estabelecimento, id_refeiçao, preço) " +
                                         "Values (@idEstabelecimento, @idRefeiçao, @preço)", conn);
                registaEstRef.Parameters.Add(idEstabelecimento);
                registaEstRef.Parameters.Add(idRefeiçao);
                registaEstRef.Parameters.Add(preço);

                registaEstRef.ExecuteNonQuery();

            }

            return id;
        }

        public int existeIngrediente(string ing, SqlConnection conn)
        {

            SqlParameter ingrediente = new SqlParameter("@ingrediente", System.Data.SqlDbType.NVarChar, 20);
            ingrediente.Value = ing;

            SqlCommand com = new SqlCommand("select id_ingrediente from Ingrediente where designaçao=@ingrediente", conn);
            com.Parameters.Add(ingrediente);


            Object id = com.ExecuteScalar();

            Int32 idIng = -1;

            if (id != null)
                idIng = (Int32)id;

            com.Parameters.Clear();

            return idIng;
        }
        public void registaIngrediente(int idR, string ing)
        {
            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "li4server1.database.windows.net";
            builder.UserID = "rgllm";
            builder.Password = "Universidade2016";
            builder.InitialCatalog = "li4";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {
                conn.Open();

                int id = existeIngrediente(ing, conn);

                if (id == -1)
                {
                    //insere ingrediente a ingrediente

                    SqlParameter ingrediente = new SqlParameter("@ingrediente", System.Data.SqlDbType.NVarChar, 20);
                    ingrediente.Value = ing;

                    SqlCommand registaIngrediente = new SqlCommand("insert into Ingrediente (designaçao) values(@ingrediente)", conn);
                    registaIngrediente.Parameters.Add(ingrediente);

                    registaIngrediente.ExecuteNonQuery();
                    registaIngrediente.Parameters.Clear();

                    id = existeIngrediente(ing, conn);
                }

                //insere ingrdienteREfeiçao

                SqlParameter idIngrediente = new SqlParameter("@idIngrediente", System.Data.SqlDbType.Int);
                idIngrediente.Value = id;

                SqlParameter idRefeiçao = new SqlParameter("@idRefeiçao", System.Data.SqlDbType.Int);
                idRefeiçao.Value = idR;

                SqlCommand com = new SqlCommand("insert into IngredienteRefeiçao (id_ingrediente, id_refeiçao) values(@idIngrediente, @idRefeiçao)", conn);
                com.Parameters.Add(idIngrediente);
                com.Parameters.Add(idRefeiçao);

                com.ExecuteNonQuery();

            }
        }

        public SortedDictionary<int, Estabelecimento> getEstabelecimentos(string refe)
        {
            SortedDictionary<int, Estabelecimento> estabelecimentos = new SortedDictionary<int, Estabelecimento>();

            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "li4server1.database.windows.net";
            builder.UserID = "rgllm";
            builder.Password = "Universidade2016";
            builder.InitialCatalog = "li4";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {
                conn.Open();
                try
                {
                    SqlParameter designacao = new SqlParameter("@designacao", System.Data.SqlDbType.NVarChar, 40);
                    designacao.Value = refe;

                    SqlDataReader reader = null;
                    SqlCommand est = new SqlCommand("select id_refeiçao, designaçao, tipo_de_cozinha, descriçao, foto_path from li4.dbo.Refeiçao where designaçao=@designacao", conn);
                    est.Parameters.Add(designacao);
                    reader = est.ExecuteReader();
                    
                    while (reader.Read())
                    {
                        int idRefeiçao = Int32.Parse(reader["id_refeiçao"].ToString());
                        string descriçao = reader["descriçao"].ToString().TrimEnd();
                        string tipo_de_cozinha = reader["tipo_de_cozinha"].ToString().TrimEnd();
                        string foto_path = reader["foto_path"].ToString().TrimEnd();

                        HashSet<string> ings = new HashSet<string>();

                        using (SqlConnection con = new SqlConnection(builder.ConnectionString))
                        {
                            con.Open();

                            SqlParameter idRefeicao = new SqlParameter("@idRefeicao", System.Data.SqlDbType.NVarChar, 40);
                            idRefeicao.Value = idRefeiçao;

                            SqlDataReader reader2 = null;


                            SqlCommand ing =
                                new SqlCommand("select designaçao from Ingrediente inner join IngredienteRefeiçao on IngredienteRefeiçao.id_ingrediente = Ingrediente.id_ingrediente " +
                                " where IngredienteRefeiçao.id_refeiçao=@idRefeicao", con);
                            ing.Parameters.Add(idRefeicao);

                            reader2 = ing.ExecuteReader();

                            while (reader2.Read())
                            {
                                ings.Add(reader2["designaçao"].ToString().TrimEnd());
                            }

                        }

                        Refeicao refeicao = new Refeicao(descriçao, refe, tipo_de_cozinha, foto_path, ings);

                        using (SqlConnection con = new SqlConnection(builder.ConnectionString))
                        {
                            con.Open();

                            SqlParameter idRefeicao = new SqlParameter("@idRefeicao", System.Data.SqlDbType.NVarChar, 40);
                            idRefeicao.Value = idRefeiçao;

                            SqlDataReader reader3 = null;
                            SqlCommand ests =new SqlCommand("select Estabelecimento.id_estabelecimento, designaçao, NIF, telefone, rede_social, url_site, classificaçao, morada, codigo_postal from li4.dbo.Estabelecimento inner join li4.dbo.EstabelecimentoRefeiçao on Estabelecimento.id_estabelecimento = EstabelecimentoRefeiçao.id_estabelecimento  where EstabelecimentoRefeiçao.id_refeiçao=@idRefeicao", con);
                            ests.Parameters.Add(idRefeicao);
                            reader3 = ests.ExecuteReader();

                            while (reader3.Read())
                            {
                                int idEstabelecimento = Int32.Parse(reader3["id_estabelecimento"].ToString());
                                string designaçao = reader3["designaçao"].ToString().TrimEnd();
                                int NIF = Int32.Parse(reader3["NIF"].ToString());
                                int telefone = Int32.Parse(reader3["telefone"].ToString());
                                string rede_social = reader3["rede_social"].ToString().TrimEnd();
                                string url_site = reader3["url_site"].ToString().TrimEnd();
                                float classificaçao = float.Parse(reader3["classificaçao"].ToString());
                                string codigo_postal = reader3["codigo_postal"].ToString().TrimEnd();
                                string morada = reader3["morada"].ToString().TrimEnd();

                                if (!estabelecimentos.ContainsKey(idEstabelecimento))
                                {
                                    HashSet<Refeicao> r = new HashSet<Refeicao>();
                                    r.Add(refeicao);
                                    Estabelecimento estabelecimento = new Estabelecimento(url_site, codigo_postal, designaçao, rede_social, telefone, NIF, classificaçao, r,morada);
                                    estabelecimentos.Add(idEstabelecimento, estabelecimento);
                                }
                                else
                                    estabelecimentos[idEstabelecimento].addRefeicao(refeicao);
                            }
                        }

                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }

            }

            return estabelecimentos;
        }
    }
}

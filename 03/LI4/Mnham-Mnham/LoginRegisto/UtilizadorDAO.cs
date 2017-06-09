using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace LoginRegisto
{
    class UtilizadorDAO
    {
        public UtilizadorDAO()
        {

        }
        public void adicionaPesquisa(int id, string pesquisa)
        {
            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "li4server1.database.windows.net";
            builder.UserID = "rgllm";
            builder.Password = "Universidade2016";
            builder.InitialCatalog = "li4";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {
                conn.Open();

                SqlParameter idU = new SqlParameter("@id", System.Data.SqlDbType.Int);
                idU.Value = id;

                SqlParameter pesquisaPar = new SqlParameter("@pesquisaPar", System.Data.SqlDbType.NChar, 50);
                pesquisaPar.Value = pesquisa;

                SqlCommand inserePesquisa = new SqlCommand("INSERT INTO li4.dbo.Pesquisa (id_utilizador, pesquisa) " +
                                     "Values (@id,@pesquisaPar)", conn);
                inserePesquisa.Parameters.Add(idU);
                inserePesquisa.Parameters.Add(pesquisaPar);

                inserePesquisa.ExecuteNonQuery();

            }
        }

        public List<String> getPesquisas(int id)
        {
            List<String> pesquisas = new List<string>();
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
                    SqlParameter idU = new SqlParameter("idU", System.Data.SqlDbType.Int);
                    idU.Value = id;

                    SqlDataReader reader = null;
                    SqlCommand pesq = new SqlCommand("select pesquisa from Pesquisa where id_utilizador=@idU", conn);

                    pesq.Parameters.Add(idU);

                    reader = pesq.ExecuteReader();

                    while (reader.Read())
                    {
                        pesquisas.Add(reader["pesquisa"].ToString());
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
            return pesquisas;
        }

        public bool existeUtilizador(SqlConnection conn, string e)
        {

            //verificar se existe utilizador com email referido
            SqlParameter email = new SqlParameter("@email", System.Data.SqlDbType.NChar, 40);
            email.Value = e;

            SqlCommand com = new SqlCommand("select count(email) from Utilizador where email=@email", conn);
            com.Parameters.Add(email);


            Int32 count = (Int32)com.ExecuteScalar();

            if (count > 0)
                return true;

            return false;
        }

        public bool registaUtilizador(string n, string e, string p)
        {

            bool exists = false;
            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "li4server1.database.windows.net";
            builder.UserID = "rgllm";
            builder.Password = "Universidade2016";
            builder.InitialCatalog = "li4";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {

                conn.Open();

                exists = existeUtilizador(conn, e);

                if (exists == false)
                {
                    //inserir utilizador

                    SqlParameter nome = new SqlParameter("@nome", System.Data.SqlDbType.NChar, 50);
                    nome.Value = n;

                    SqlParameter email1 = new SqlParameter("@email1", System.Data.SqlDbType.NChar);
                    email1.Value = e;

                    SqlParameter pass = new SqlParameter("@pass", System.Data.SqlDbType.NChar, 20);
                    pass.Value = p;

                    SqlCommand registaUtilizador = new SqlCommand("INSERT INTO li4.dbo.Utilizador ( nome, email, password) " +
                                         "Values (@nome, @email1, @pass)", conn);
                    registaUtilizador.Parameters.Add(nome);
                    registaUtilizador.Parameters.Add(email1);
                    registaUtilizador.Parameters.Add(pass);

                    registaUtilizador.ExecuteNonQuery();

                    Console.WriteLine("args4:");
                }
            }
            return !exists;
        }

        public int login(string mail, string pass)
        {
            int id = -1;

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
                    SqlParameter email = new SqlParameter("@email", System.Data.SqlDbType.NChar);
                    email.Value = mail;

                    SqlParameter password = new SqlParameter("@password", System.Data.SqlDbType.NChar, 20);
                    password.Value = pass;

                    SqlDataReader reader = null;
                    SqlCommand getID = new SqlCommand("select id_utilizador from Utilizador where email=@email and password=@password", conn);

                    getID.Parameters.Add(email);
                    getID.Parameters.Add(password);

                    reader = getID.ExecuteReader();

                    while (reader.Read())
                    {
                        id = Int32.Parse(reader["id_utilizador"].ToString());
                    }

                    Console.WriteLine("args4:");
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
            return id;
        }

    }
}

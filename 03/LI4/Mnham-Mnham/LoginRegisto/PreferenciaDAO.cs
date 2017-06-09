using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace LoginRegisto
{
    class PreferenciaDAO
    {
        public PreferenciaDAO() { }

        public int existePreferencia(bool t, string i, string tc)
        {

            int existe = -1;

            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "your.azure.server";
            builder.UserID = "id";
            builder.Password = "xxxx";
            builder.InitialCatalog = "xxx";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {

                conn.Open();


                //verificar se já existe preferência
                SqlParameter tipo = new SqlParameter("@tipo", System.Data.SqlDbType.Bit);
                tipo.Value = t;



                SqlParameter ingrediente = new SqlParameter("@ingrediente", System.Data.SqlDbType.NChar, 20);
                ingrediente.Value = i;


                SqlParameter tipo_cozinha = new SqlParameter("@cozinha", System.Data.SqlDbType.NChar, 20);
                tipo_cozinha.Value = tc;

                SqlCommand com = new SqlCommand("select id_preferencia from Preferencia where tipo=@tipo and ingrediente=@ingrediente and tipo_de_cozinha=@cozinha", conn);
                com.Parameters.Add(tipo);
                com.Parameters.Add(ingrediente);
                com.Parameters.Add(tipo_cozinha);

                Console.WriteLine("args5:");

                Object id = com.ExecuteScalar();

                Int32 idPre;

                if (id != null)
                {
                    idPre = (Int32)id;
                    existe = idPre;
                    Console.WriteLine(id);
                }
            }

            return existe;
        }


        public void registaPreferencia(int idU, int idP, bool t, string i, string tc)
        {
            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "your.azure.server";
            builder.UserID = "id";
            builder.Password = "xxxx";
            builder.InitialCatalog = "xxx";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {

                conn.Open();

                SqlParameter idUtilizador = new SqlParameter("@idUtilizador", System.Data.SqlDbType.Int);
                idUtilizador.Value = idU;

                SqlParameter tipo = new SqlParameter("@tipo", System.Data.SqlDbType.Bit);
                tipo.Value = t;

                SqlParameter ingrediente = new SqlParameter("@ingrediente", System.Data.SqlDbType.NChar, 20);
                ingrediente.Value = i;

                SqlParameter tipo_cozinha = new SqlParameter("@cozinha", System.Data.SqlDbType.NChar, 20);
                tipo_cozinha.Value = tc;


                SqlCommand registaPreferencia, registaUtilizadorPreferencia;

                SqlParameter idPreferencia;


                if (idP == -1)
                {
                    registaPreferencia = new SqlCommand("INSERT INTO li4.dbo.Preferencia ( tipo, ingrediente, tipo_de_cozinha) " +
                                     "Values (@tipo, @ingrediente, @cozinha)", conn);
                    registaPreferencia.Parameters.Add(tipo);
                    registaPreferencia.Parameters.Add(ingrediente);
                    registaPreferencia.Parameters.Add(tipo_cozinha);
                    registaPreferencia.ExecuteNonQuery();

                    registaPreferencia.Parameters.Clear();



                    SqlCommand com = new SqlCommand("select id_preferencia from Preferencia where tipo=@tipo and ingrediente=@ingrediente and tipo_de_cozinha=@cozinha", conn);
                    com.Parameters.Add(tipo);
                    com.Parameters.Add(ingrediente);
                    com.Parameters.Add(tipo_cozinha);

                    Int32 idd = (Int32)com.ExecuteScalar();

                    registaPreferencia.Parameters.Clear();

                    idPreferencia = new SqlParameter("@idPreferencia", System.Data.SqlDbType.Int);
                    idPreferencia.Value = idd;

                }
                else
                {
                    idPreferencia = new SqlParameter("@idPreferencia", System.Data.SqlDbType.Int);
                    idPreferencia.Value = idP;
                }

                SqlCommand co = new SqlCommand("select count(id_utilizador) from UtilizadorPreferencia where id_utilizador=@idUtilizador and id_preferencia= @idPreferencia", conn);
                co.Parameters.Add(idUtilizador);
                co.Parameters.Add(idPreferencia);


                Int32 count = (Int32)co.ExecuteScalar();

                co.Parameters.Clear();

                if (count == 0)
                {
                    registaUtilizadorPreferencia = new SqlCommand("INSERT INTO UtilizadorPreferencia ( id_utilizador, id_preferencia) " +
                                         "Values (@idUtilizador, @idPreferencia)", conn);
                    registaUtilizadorPreferencia.Parameters.Add(idUtilizador);
                    registaUtilizadorPreferencia.Parameters.Add(idPreferencia);


                    registaUtilizadorPreferencia.ExecuteNonQuery();
                }

            }
        }


        public HashSet<String> getPreferencias(int idU, bool t)
        {
            HashSet<String> preferencias = new HashSet<string>();

            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "your.azure.server";
            builder.UserID = "id";
            builder.Password = "xxxx";
            builder.InitialCatalog = "xxx";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {

                conn.Open();

                SqlParameter idUtilizador = new SqlParameter("@idUtilizador", System.Data.SqlDbType.Int);
                idUtilizador.Value = idU;

                SqlParameter tipo = new SqlParameter("@tipo", System.Data.SqlDbType.Bit);
                tipo.Value = t;

                try
                {
                    string ing, coz;
                    SqlDataReader reader;
                    SqlCommand get =
                        new SqlCommand("select ingrediente,tipo_de_cozinha from Preferencia inner join UtilizadorPreferencia on UtilizadorPreferencia.id_preferencia = Preferencia.id_preferencia "
                        + " where id_utilizador=@idUtilizador and tipo=@tipo", conn);
                    get.Parameters.Add(idUtilizador);
                    get.Parameters.Add(tipo);

                    reader = get.ExecuteReader();
                    while (reader.Read())
                    {
                        ing = reader["ingrediente"].ToString();
                        coz = reader["tipo_de_cozinha"].ToString();
                        if (ing.CompareTo("                    ") != 0)
                            preferencias.Add(ing);
                        if (coz.CompareTo("                    ") != 0)
                            preferencias.Add(coz);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
            return preferencias;
        }


        public bool existePreferencia(SqlConnection conn, int idU, string p, bool t)
        {
            //verificar se existe preferência para o utilizador

            SqlParameter idUtilizador = new SqlParameter("@idUtilizador", System.Data.SqlDbType.Int);
            idUtilizador.Value = idU;

            SqlParameter tipo = new SqlParameter("@tip", System.Data.SqlDbType.Bit);
            tipo.Value = t;

            SqlParameter preferencia = new SqlParameter("@pref", System.Data.SqlDbType.NChar, 20);
            preferencia.Value = p;

            SqlCommand com =
                new SqlCommand("select count(UtilizadorPreferencia.id_utilizador) from UtilizadorPreferencia inner join Preferencia on Preferencia.id_preferencia=UtilizadorPreferencia.id_preferencia " +
                " where UtilizadorPreferencia.id_utilizador=@idUtilizador and Preferencia.tipo=@tip and UtilizadorPreferencia.id_preferencia=" +
                "(select Preferencia.id_preferencia from Preferencia  where Preferencia.ingrediente=@pref or Preferencia.tipo_de_cozinha=@pref)", conn);
            com.Parameters.Add(idUtilizador);
            com.Parameters.Add(tipo);
            com.Parameters.Add(preferencia);


            Int32 count = (Int32)com.ExecuteScalar();

            if (count > 0)
                return true;

            return false;
        }

        public bool eliminaPreferencia(int idU, string p, bool t)
        {
            bool existe = false;

            SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
            builder.DataSource = "your.azure.server";
            builder.UserID = "id";
            builder.Password = "xxxx";
            builder.InitialCatalog = "xxx";

            using (SqlConnection conn = new SqlConnection(builder.ConnectionString))
            {

                conn.Open();

                existe = existePreferencia(conn, idU, p, t);

                if (existe == true)
                {
                    SqlParameter idUtilizador = new SqlParameter("@idUtilizador", System.Data.SqlDbType.Int);
                    idUtilizador.Value = idU;

                    SqlParameter tipo = new SqlParameter("@tip", System.Data.SqlDbType.Bit);
                    tipo.Value = t;

                    SqlParameter preferencia = new SqlParameter("@pref", System.Data.SqlDbType.NChar, 20);
                    preferencia.Value = p;

                    SqlCommand com =
                        new SqlCommand("delete from UtilizadorPreferencia where UtilizadorPreferencia.id_utilizador=@idUtilizador and UtilizadorPreferencia.id_preferencia = " +
                        " (select Preferencia.id_preferencia from Preferencia inner join UtilizadorPreferencia on Preferencia.id_preferencia = UtilizadorPreferencia.id_preferencia " +
                        " where Preferencia.tipo = @tip and UtilizadorPreferencia.id_utilizador = @idUtilizador and (ingrediente = @pref or tipo_de_cozinha =@pref))", conn);
                    com.Parameters.Add(idUtilizador);
                    com.Parameters.Add(tipo);
                    com.Parameters.Add(preferencia);

                    com.ExecuteNonQuery();
                }

            }
            return existe;
        }
    }
}

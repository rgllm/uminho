using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace LoginRegisto
{
    class CriticaDAO
    {
        public CriticaDAO() { }

        public void efetuaCritica(int idE, int idU, int cla, string com)
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

                SqlParameter idEstabelecimento = new SqlParameter("@idEstabelecimento", System.Data.SqlDbType.Int);
                idEstabelecimento.Value = idE;

                SqlParameter classificacao = new SqlParameter("@classificacao", System.Data.SqlDbType.Decimal);
                classificacao.Value = cla;

                SqlParameter comentario = new SqlParameter("@comentario", System.Data.SqlDbType.NChar, 100);
                comentario.Value = com;

                SqlCommand registaCritica;

                registaCritica = new SqlCommand("INSERT INTO Critica ( classificacao, comentario, id_utilizador, id_estabelecimento) " +
                                         "Values (@classificacao, @comentario, @idUtilizador, @idEstabelecimento)", conn);
                registaCritica.Parameters.Add(classificacao);
                registaCritica.Parameters.Add(comentario);
                registaCritica.Parameters.Add(idUtilizador);
                registaCritica.Parameters.Add(idEstabelecimento);


                registaCritica.ExecuteNonQuery();

                //Calcular a nova classificação do restaurante!


                registaCritica.Parameters.Clear();

                try
                {


                    SqlDataReader reader = null;
                    SqlCommand contaEst = new SqlCommand("select sum(classificacao) as su,count(id_estabelecimento) as co from Critica where id_estabelecimento=@idEstabelecimento", conn);
                    contaEst.Parameters.Add(idEstabelecimento);

                    reader = contaEst.ExecuteReader();

                    int classi, tot;
                    classi = tot = 0;

                    while (reader.Read())
                    {
                        classi = Int32.Parse(reader["su"].ToString());
                        tot = Int32.Parse(reader["co"].ToString());
                    }

                    reader.Close();

                    contaEst.Parameters.Clear();

                    float class_final = (float)(classi + cla) / (float)(tot + 1);

                    SqlParameter classif = new SqlParameter("@classif", System.Data.SqlDbType.Decimal);
                    classif.Value = class_final;


                    SqlCommand alteraClassificacao =
                        new SqlCommand("update Estabelecimento set classificaçao=@classif where id_estabelecimento=@idEstabelecimento ", conn);
                    alteraClassificacao.Parameters.Add(classif);
                    alteraClassificacao.Parameters.Add(idEstabelecimento);

                    alteraClassificacao.ExecuteNonQuery();

                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }

            }
        }
    }
}

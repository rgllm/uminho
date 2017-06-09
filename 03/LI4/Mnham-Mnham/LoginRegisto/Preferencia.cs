using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LoginRegisto
{
    class Preferencia
    {
        public HashSet<String> preferencias;
        public HashSet<String> naoPreferencias;

        PreferenciaDAO pDAO;

        public Preferencia(int idU)
        {
            pDAO = new PreferenciaDAO();
            preferencias = new HashSet<string>();
            preferencias = getPreferencias(idU);
            naoPreferencias = new HashSet<string>();
            naoPreferencias = getNaoPreferencias(idU);
        }

        public HashSet<String> getPreferencias(int idU)
        {
            HashSet<String> pr = new HashSet<string>(pr = pDAO.getPreferencias(idU, true));

            for (int i = 0; i < pr.Count(); i++)
            {
                preferencias.Add(pr.ElementAt(i).Trim());
            }

            return preferencias;
        }

        public HashSet<String> getNaoPreferencias(int idU)
        {
            HashSet<String> pr = pDAO.getPreferencias(idU, false);

            for (int i = 0; i < pr.Count(); i++)
            {
                naoPreferencias.Add(pr.ElementAt(i).Trim());
            }

            return naoPreferencias;
        }


        public void registaPreferenciaPontual(string preferencia)
        {
            preferencias.Add(preferencia);
        }

        public void registaNaoPreferenciaPontual(string naoPreferencia)
        {
            preferencias.Add(naoPreferencia);
        }

        public void removePreferenciaPontual(string preferencia)
        {
            preferencias.Remove(preferencia);
        }

        public void removeNaoPreferenciaPontual(string naoPreferencia)
        {
            preferencias.Remove(naoPreferencia);
        }


        public void registaPreferencia(int idU, string ingrediente, string tipo_cozinha)
        {
            if (!string.IsNullOrEmpty(ingrediente))
                registaPreferenciaPontual(ingrediente);
            else if (!string.IsNullOrEmpty(tipo_cozinha))
                registaPreferenciaPontual(tipo_cozinha);

            adicionaPreferencia(idU, true, ingrediente, tipo_cozinha);
        }

        public void registaNaoPreferencia(int idU, string ingrediente, string tipo_cozinha)
        {
            if (ingrediente.CompareTo("") != 0)
                registaNaoPreferenciaPontual(ingrediente);
            else if (tipo_cozinha.CompareTo("") != 0)
                registaNaoPreferenciaPontual(tipo_cozinha);

            adicionaPreferencia(idU, false, ingrediente, tipo_cozinha);
        }

        private void adicionaPreferencia(int idU, bool tipo, string ingrediente, string tipo_cozinha)
        {
            int idPreferencia = pDAO.existePreferencia(tipo, ingrediente, tipo_cozinha);
            pDAO.registaPreferencia(idU, idPreferencia, tipo, ingrediente, tipo_cozinha);
        }



        public bool removePreferencia(int idU, string preferencia)
        {
            preferencias.Remove(preferencia);
            return eliminaPreferencia(idU, preferencia, true);
        }

        public bool removeNaoPreferencia(int idU, string preferencia)
        {
            naoPreferencias.Remove(preferencia);
            return eliminaPreferencia(idU, preferencia, false);
        }

        private bool eliminaPreferencia(int idU, string preferencia, bool tipo)
        {
            return pDAO.eliminaPreferencia(idU, preferencia, tipo);
        }

    }
}

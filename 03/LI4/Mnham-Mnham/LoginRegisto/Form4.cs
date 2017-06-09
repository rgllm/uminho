using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace LoginRegisto
{
    public partial class Form4 : Form
    {
        private MnhamMnham mnham;

        public Form4(MnhamMnham m)
        {
            InitializeComponent();
            mnham = m;
            string boasVindas = "Bem vindo ao Mnham-Mnham!";
            mensagens.Rows.Add(new String[] { boasVindas, "" });
        }

        private void button1_Click(object sender, EventArgs e)
        {
            inputTxt.Focus();
            if (string.IsNullOrEmpty(inputTxt.Text))
                return;
            string inp = inputTxt.Text;
            mensagens.Rows.Add(new String[] { "", inp });

            inputTxt.Clear();
            string resposta = mnham.getResposta(inp);
            mensagens.Rows.Add(new String[] { resposta, "" });

            mensagens.FirstDisplayedScrollingRowIndex = mensagens.RowCount - 1;



        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {


        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void tableLayoutPanel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void MnhamMnham_Load(object sender, EventArgs e)
        {

        }
    }
}

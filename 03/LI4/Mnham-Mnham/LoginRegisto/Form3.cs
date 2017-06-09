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
    public partial class Form3 : Form
    {

        private MnhamMnham mnham;

        public Form3(MnhamMnham m)
        {
            InitializeComponent();
            mnham = m;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string nome = textBox1.Text;
            string email = textBox2.Text;
            string pass = textBox3.Text;

            bool res = mnham.registaUtilizador(nome, email, pass);
            if (res == false)
                MessageBox.Show("Email de utilizador existente", "Erro", MessageBoxButtons.OK, MessageBoxIcon.Error);
            else
            {
                this.Hide();
                Form1 f = new Form1(mnham);
                f.ShowDialog();
            }

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Hide();
            Form1 f = new Form1(mnham);
            f.ShowDialog();
        }
    }
}

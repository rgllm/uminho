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
    public partial class Form2 : Form
    {
        private MnhamMnham mnham;
        public Form2(MnhamMnham m)
        {
            InitializeComponent();
            mnham = m;
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string mail = textBox1.Text;
            string pass = textBox2.Text;
            int res = mnham.loginUtilizador(mail, pass);
            if (res != -1) {
                this.Hide();
                Form4 f = new Form4(mnham);
                f.ShowDialog();
            }
            else
            {

                MessageBox.Show("Login errado", "Erro", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Hide();
            Form1 f = new Form1(mnham);
            f.ShowDialog();
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }
    }
}

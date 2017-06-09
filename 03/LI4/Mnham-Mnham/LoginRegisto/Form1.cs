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
    public partial class Form1 : Form
    {

       private MnhamMnham mnham;
        public Form1(MnhamMnham m)
        {
            InitializeComponent();
            mnham = m;
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Hide();
            Form2 f2 = new LoginRegisto.Form2(mnham);
            f2.ShowDialog();

        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Hide();
            Form3 f3 = new LoginRegisto.Form3(mnham);
            f3.ShowDialog();
        }
    }
}

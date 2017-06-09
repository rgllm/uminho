namespace LoginRegisto
{
    partial class Form4
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle4 = new System.Windows.Forms.DataGridViewCellStyle();
            this.inputTxt = new System.Windows.Forms.TextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.mensagens = new System.Windows.Forms.DataGridView();
            this.servidor = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.utilizador = new System.Windows.Forms.DataGridViewTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.mensagens)).BeginInit();
            this.SuspendLayout();
            // 
            // inputTxt
            // 
            this.inputTxt.Location = new System.Drawing.Point(10, 400);
            this.inputTxt.Name = "inputTxt";
            this.inputTxt.Size = new System.Drawing.Size(227, 20);
            this.inputTxt.TabIndex = 0;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(256, 397);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(36, 23);
            this.button1.TabIndex = 1;
            this.button1.Text = ">>";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // mensagens
            // 
            this.mensagens.AccessibleRole = System.Windows.Forms.AccessibleRole.None;
            this.mensagens.AllowUserToAddRows = false;
            this.mensagens.AllowUserToDeleteRows = false;
            this.mensagens.AllowUserToResizeColumns = false;
            this.mensagens.AllowUserToResizeRows = false;
            dataGridViewCellStyle3.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.mensagens.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle3;
            this.mensagens.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.mensagens.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.mensagens.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.DisplayedCells;
            this.mensagens.BackgroundColor = System.Drawing.Color.White;
            this.mensagens.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.mensagens.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.mensagens.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            this.mensagens.ColumnHeadersHeight = 15;
            this.mensagens.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.DisableResizing;
            this.mensagens.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.servidor,
            this.utilizador});
            dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle4.BackColor = System.Drawing.SystemColors.Window;
            dataGridViewCellStyle4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle4.ForeColor = System.Drawing.Color.Black;
            dataGridViewCellStyle4.SelectionBackColor = System.Drawing.Color.White;
            dataGridViewCellStyle4.SelectionForeColor = System.Drawing.Color.Black;
            dataGridViewCellStyle4.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.mensagens.DefaultCellStyle = dataGridViewCellStyle4;
            this.mensagens.Location = new System.Drawing.Point(10, 12);
            this.mensagens.Name = "mensagens";
            this.mensagens.ReadOnly = true;
            this.mensagens.RowHeadersVisible = false;
            this.mensagens.RowTemplate.DefaultCellStyle.BackColor = System.Drawing.Color.White;
            this.mensagens.RowTemplate.DefaultCellStyle.SelectionBackColor = System.Drawing.Color.White;
            this.mensagens.RowTemplate.DefaultCellStyle.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.mensagens.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.mensagens.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.CellSelect;
            this.mensagens.Size = new System.Drawing.Size(282, 366);
            this.mensagens.TabIndex = 0;
            this.mensagens.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);
            // 
            // servidor
            // 
            this.servidor.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.None;
            this.servidor.FillWeight = 200F;
            this.servidor.HeaderText = " ";
            this.servidor.Name = "servidor";
            this.servidor.ReadOnly = true;
            this.servidor.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.servidor.Width = 190;
            // 
            // utilizador
            // 
            this.utilizador.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.None;
            this.utilizador.FillWeight = 70F;
            this.utilizador.HeaderText = " ";
            this.utilizador.Name = "utilizador";
            this.utilizador.ReadOnly = true;
            this.utilizador.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.utilizador.Width = 90;
            // 
            // Form4
            // 
            this.AcceptButton = this.button1;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(304, 441);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.inputTxt);
            this.Controls.Add(this.mensagens);
            this.Name = "Form4";
            this.Text = "MnhamMnham";
            this.Load += new System.EventHandler(this.MnhamMnham_Load);
            ((System.ComponentModel.ISupportInitialize)(this.mensagens)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox inputTxt;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.DataGridView mensagens;
        private System.Windows.Forms.DataGridViewTextBoxColumn servidor;
        private System.Windows.Forms.DataGridViewTextBoxColumn utilizador;
    }
}


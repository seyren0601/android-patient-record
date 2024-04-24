using API_MEDICAL.Entities;
using Microsoft.EntityFrameworkCore;

namespace API_MEDICAL.Data
{
    public class DatabaseContext: DbContext
    {
        string ConnectionString = "server=localhost;database=patient_record;user=root;password=porsche0601";
        public DbSet<Thuoc> DbThuoc { get; set; }
        public DbSet<LoaiBenh> DbLoaiBenh { get; set; }
        public DbSet<NhomBenh> DbNhomBenh { get;set; }
        public DbSet<Benh> DbBenh { get; set; }
        public DbSet<BenhNhan> DbBenhNhan { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseMySQL(ConnectionString);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}

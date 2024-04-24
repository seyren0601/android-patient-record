using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API_MEDICAL.Entities
{
    [Table("NODE", Schema="PATIENT_RECORD")]
    public class NhomBenh
    {
        [Key]
        [StringLength(50)]
        public string NODE_ID { get; set; }
        public string NODE_NAME { get; set; }
        [StringLength(50)]
        [ForeignKey("NhomBenh")]
        public string PARENT_NODE { get; set; }
        ICollection<LoaiBenh> DanhSach_LoaiBenh { get; set; }
    }
}

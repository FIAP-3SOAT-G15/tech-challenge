variable "region" {
  type    = string
  default = "us-east-1"
}

variable "website_bucket_name" {
  type = string
  default = "fiap-3soat-g15"
}

variable "tags" {
  type = map(string)
  default = {
    managed_by_terraform = true
  }
}

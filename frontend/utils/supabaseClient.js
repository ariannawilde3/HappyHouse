import { createClient } from '@supabase/supabase-js'

const supabaseUrl = 'https://yjyoecwkthmhmafnvxtp.supabase.co'     // ← your real URL
const supabaseAnonKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlqeW9lY3drdGhtaG1hZm52eHRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM1NDAyMjIsImV4cCI6MjA3OTExNjIyMn0.RpfLpOEDH_OIft9mbk5V_M2CblNiCQiObC-9SP5lk3E'               // ← your real key

export const supabase = createClient(supabaseUrl, supabaseAnonKey)

// const supabaseUrl = import.meta.env.VITE_SUPABASE_URL 
// const supabaseAnonKey = import.meta.env.VITE_SUPABASE_ANON_KEY